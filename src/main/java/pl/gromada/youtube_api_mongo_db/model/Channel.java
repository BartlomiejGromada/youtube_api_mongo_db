package pl.gromada.youtube_api_mongo_db.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "channels")
public class Channel {

    @Id
    private String id;
    private String url;
    private String title;
    private String description;
    private String imageURL;
    private ChannelStatistics channelStatistics;
}
