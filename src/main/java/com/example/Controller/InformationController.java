package com.example.Controller;

import com.example.Model.Information;
import com.example.Service.InformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Jassy on 2017/2/28.
 * description:
 */
@Controller
public class InformationController {

    @Autowired
    InformationService informationService;

    Logger logger= LoggerFactory.getLogger(InformationController.class);

}
