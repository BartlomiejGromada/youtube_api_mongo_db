package pl.gromada.youtube_api_mongo_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pl.gromada.youtube_api_mongo_db.model.Channel;
import pl.gromada.youtube_api_mongo_db.service.ChannelService;

@Controller
public class ChannelController {

    private ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("/search")
    public String searchChannel() {
        return "search";
    }

    @PostMapping("/search")
    public String searchChannelResult(Model model, @RequestParam String channelId) {
        Channel channel = channelService.findChannelById(channelId);
        if(channel!= null)
            model.addAttribute("channel", channel);
        else {
            model.addAttribute("notResult", true);
            model.addAttribute("channelId", channelId);
        }

        return "search";
    }

    @PostMapping("/add")
    public String addChannel(@RequestBody Channel channel) {
        channelService.saveChannel(channel);

        return "redirect:/search";
    }
}
