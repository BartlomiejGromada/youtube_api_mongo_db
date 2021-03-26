package pl.gromada.youtube_api_mongo_db.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.gromada.youtube_api_mongo_db.model.Channel;
import pl.gromada.youtube_api_mongo_db.repository.ChannelRepository;
import pl.gromada.youtube_api_mongo_db.ytapi.YtApi;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ChannelService {

    private static final String YOUTUBE_SEARCH_FIELDS =
            "items(id,snippet/title,snippet/description,snippet/thumbnails/medium/url)";
    private static final String URL_YT_BEGINNING = "https://www.youtube.com/channel/";
    private ChannelRepository channelRepository;

    @Value("${youtube.api.key}")
    private String apiKey;


    @Autowired
    public ChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public Channel findChannelById(String idChannel) {
        Channel channel = null;

        try {
            //get YouTube object
            YouTube youTube = YtApi.getService();

            //what we want to get
            YouTube.Search.List search = youTube.search().list("id,snippet");

            //set credentials
            search.setKey(apiKey);

            //want info about channels
            search.setType("channel");

            //search channel base on idChannel
            search.setQ(idChannel);

            //set the fields that we're going to use
            search.setFields(YOUTUBE_SEARCH_FIELDS);

            //perform search and parse the results
            SearchListResponse searchListResponse = search.execute();
            List<SearchResult> searchResultList = searchListResponse.getItems();

            SearchResult searchResult = null;
            if (!searchResultList.isEmpty())
                searchResult = searchListResponse.getItems().get(0);

            if (searchResult != null) {
                channel = new Channel();
                channel.setUrl(URL_YT_BEGINNING + searchResult.getId().getChannelId());
                channel.setTitle(searchResult.getSnippet().getTitle());
                channel.setDescription(searchResult.getSnippet().getDescription());
                channel.setImageURL(searchResult.getSnippet().getThumbnails().getMedium().getUrl());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return channel;
    }

    public void saveChannel(Channel channel) {
        channelRepository.save(channel);
    }

    public List<Channel> findAllChannels() {
        return channelRepository.findAll();
    }

    public void deleteChannelById(String id) {
        channelRepository.deleteById(id);
    }
}
