package pl.gromada.youtube_api_mongo_db.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.gromada.youtube_api_mongo_db.model.Channel;

import java.util.List;

@Repository
public interface ChannelRepository extends MongoRepository<Channel, String> {

    List<Channel> findByTitle(String title);
}
