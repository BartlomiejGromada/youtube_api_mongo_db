package pl.gromada.youtube_api_mongo_db.service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.gromada.youtube_api_mongo_db.model.Channel;
import pl.gromada.youtube_api_mongo_db.model.ChannelStatistics;
import pl.gromada.youtube_api_mongo_db.repository.ChannelRepository;
import pl.gromada.youtube_api_mongo_db.ytapi.YtApi;

import java.io.IOException;
import java.util.List;

@Service
public class ChannelService {

    private static final String YOUTUBE_SEARCH_FIELDS =
            "items(id,snippet/title,snippet/description,snippet/thumbnails/medium/url,statistics)";
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
            YouTube.Channels.List search = youTube.channels().list("id,snippet, statistics");

            //set credentials
            search.setKey(apiKey);

            //search channel base on idChannel
            search.setId(idChannel);

            //set the fields that we're going to use
            search.setFields(YOUTUBE_SEARCH_FIELDS);

            //perform search and parse the results
            ChannelListResponse channelListResponse = search.execute();
            List<com.google.api.services.youtube.model.Channel> searchChannelList
                    = channelListResponse.getItems();

            com.google.api.services.youtube.model.Channel searchChannel = null;
            if (searchChannelList != null && !searchChannelList.isEmpty())
                searchChannel = searchChannelList.get(0);
            if (searchChannel != null) {
                channel = new Channel();
                channel.setUrl(URL_YT_BEGINNING + searchChannel.getId());
                channel.setTitle(searchChannel.getSnippet().getTitle());
                channel.setDescription(searchChannel.getSnippet().getDescription());
                channel.setImageURL(searchChannel.getSnippet().getThumbnails().getMedium().getUrl());
                channel.setChannelStatistics(returnStatisticsChannel(searchChannel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return channel;
    }

    private ChannelStatistics returnStatisticsChannel(com.google.api.services.youtube.model.Channel searchChannel) {
        ChannelStatistics channelStatistics = new ChannelStatistics();
        channelStatistics.setSubscriberCount(searchChannel.getStatistics().getSubscriberCount());
        channelStatistics.setVideoCount(searchChannel.getStatistics().getVideoCount());
        channelStatistics.setViewCount(searchChannel.getStatistics().getViewCount());
        return channelStatistics;
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
