package com.sg.Music.controller;

import com.sg.Music.entities.*;
import com.sg.Music.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @GetMapping("addLabel")
    public String addLabel(Model model) {
        // Add a new Label object to the model for form-backing
        model.addAttribute("label", new Label());
        return "addLabel";
    }


    @PostMapping("addLabel")
    public String addGenre(@Valid Label label, BindingResult result, HttpServletRequest request, Model model) {

        if (StringUtils.isEmpty(label.getName())) {
            FieldError error = new FieldError("label", "name", "Label must must have a name");
            result.addError(error);
        }

        if (StringUtils.isEmpty(label.getDescription())) {
            FieldError error = new FieldError("label", "description", "Label must must have a description");
            result.addError(error);
        }

        if (result.hasErrors()) {
            return "addLabel";
        }

        service.addLabel(label);

        return "redirect:/labels";
    }

    @GetMapping("editLabel")
    public String editLabel(Integer id, Model model) {
        Label label = service.getLabelByID(id);
        model.addAttribute("label", label);
        return "editLabel";
    }


    @PostMapping("editLabel")
    public String editGenre(@Valid Label label, BindingResult result, HttpServletRequest request, Model model) {

        if (StringUtils.isEmpty(label.getName())) {
            FieldError error = new FieldError("label", "name", "Label must must have a name");
            result.addError(error);
        }

        if (StringUtils.isEmpty(label.getDescription())) {
            FieldError error = new FieldError("label", "description", "Label must must have a description");
            result.addError(error);
        }

        if (result.hasErrors()) {
            return "editLabel";
        }

        service.updateLabel(label);

        return "redirect:/labels";
    }


}
