package com.sg.Music.controller;

import com.sg.Music.entities.*;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LabelController {

    @Autowired
    ServiceInterface service;

    @GetMapping("labels")
    public String displayLabels(Model model) {
        List<Label> labels = service.getAllLabels();
        model.addAttribute("labels", labels);

        return "labels";
    }

    @GetMapping("labelDetail")
    public String labelDetail(Integer id, Model model) {
        Label label = service.getLabelByID(id);
        model.addAttribute("label", label);

        return "labelDetail";
    }

    @PostMapping("deleteLabel")
    public String deleteLabel(Integer id) {
        service.deleteLabelByID(id);

        return "redirect:/labels";
    }

}
