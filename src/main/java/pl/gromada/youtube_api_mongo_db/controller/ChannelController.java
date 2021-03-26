package pl.gromada.youtube_api_mongo_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.gromada.youtube_api_mongo_db.model.Channel;
import pl.gromada.youtube_api_mongo_db.service.ChannelService;

import java.util.List;

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
        if (channel != null)
            model.addAttribute("channel", channel);
        else {
            model.addAttribute("notResult", true);
            model.addAttribute("channelId", channelId);
        }

        return "search";
    }

    @PostMapping("/add")
    public String addChannel(@ModelAttribute Channel channel, RedirectAttributes redirectAttributes) {
        channelService.saveChannel(channel);
        redirectAttributes.addFlashAttribute("message", "Channel has been added to database");
        return "redirect:/search";
    }

    @GetMapping("/channels")
    public String databaseChannels(Model model) {
        List<Channel> channels = channelService.findAllChannels();
        model.addAttribute("channels", channels);
        return "databaseChannels";
    }

    @GetMapping("/channels/delete/{id}")
    public String deleteChannel(@PathVariable String id, RedirectAttributes redirectAttributes) {
        channelService.deleteChannelById(id);
        redirectAttributes.addFlashAttribute("message", "Channel with id: " + id + " has been deleted");
        return "redirect:/channels";
    }
}
