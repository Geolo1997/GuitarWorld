package pers.geolo.guitarworld.entity;

import java.util.List;

/**
 * @author 桀骜(Geolo)
 * @version 1.0
 * @date 2019/7/31
 */
public class GetWorksListSuccessEvent {

    private List<Works> worksList;

    public GetWorksListSuccessEvent(List<Works> worksList) {
        this.worksList = worksList;
    }
}
