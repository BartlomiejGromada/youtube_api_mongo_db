package pl.gromada.youtube_api_mongo_db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelStatistics {

    private BigInteger viewCount;
    private BigInteger subscriberCount;
    private BigInteger videoCount;
}
