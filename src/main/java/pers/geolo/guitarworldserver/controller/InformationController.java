package pers.geolo.guitarworldserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.geolo.guitarworldserver.annotation.Auth;
import pers.geolo.guitarworldserver.annotation.AuthType;
import pers.geolo.guitarworldserver.entity.Information;
import pers.geolo.guitarworldserver.entity.ResponseEntity;
import pers.geolo.guitarworldserver.service.InformationService;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/8/1
 */
@Controller
@RequestMapping("/information")
@Auth(AuthType.LOGGED)
public class InformationController {

    @Autowired
    InformationService informationService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Information>> getBannerInformations() {
        List<Information> informationList = informationService.getBannerInformations();
        return new ResponseEntity<>(0, informationList, null);
    }
}
