package my.project.modules.ums.dto;


import lombok.Getter;
import lombok.Setter;
import my.project.modules.ums.model.UmsMenu;

import java.util.List;

/**
 * 後台菜單節點封裝
 *
 * @author : kevin Chang
 */
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {

    private List<UmsMenuNode> children;
}
