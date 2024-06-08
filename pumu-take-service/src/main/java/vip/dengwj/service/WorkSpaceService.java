package vip.dengwj.service;

import vip.dengwj.vo.BusinessDataVO;
import vip.dengwj.vo.OverviewDishesVO;
import vip.dengwj.vo.OverviewOrdersVO;

public interface WorkSpaceService {

    BusinessDataVO businessData();

    OverviewOrdersVO overviewOrders();

    OverviewDishesVO overviewDishes();

    OverviewDishesVO overviewSetmeals();
}
