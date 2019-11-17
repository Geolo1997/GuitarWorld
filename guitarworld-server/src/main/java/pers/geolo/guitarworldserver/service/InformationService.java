package pers.geolo.guitarworldserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.geolo.guitarworldserver.dao.InformationMapper;
import pers.geolo.guitarworldserver.entity.Information;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/8/1
 */
@Service
public class InformationService {

    @Autowired
    InformationMapper informationMapper;

    public List<Information> getBannerInformations() {
        return informationMapper.selectAll();
    }
}
