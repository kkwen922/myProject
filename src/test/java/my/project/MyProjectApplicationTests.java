package my.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import my.project.modules.oms.model.OmsCompany;
import my.project.modules.oms.model.OmsOrganization;
import my.project.modules.oms.service.OmsCompanyService;
import my.project.modules.oms.service.OmsOrganizationService;
import my.project.modules.ums.mapper.UmsAdminMapper;
import my.project.modules.ums.model.UmsAdmin;
import my.project.modules.ums.service.UmsAdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyProjectApplicationTests {

    @Autowired
    private OmsOrganizationService organizationService;

    @Autowired
    private OmsCompanyService omsCompanyService;

    @Autowired
    private UmsAdminService umsAdminService;

    @Test
    void omsCompanyTest() {

        System.out.println(omsCompanyService.list());
        System.out.println(omsCompanyService.getById(1L));
    }


    @Test
    void umsAdminTest() {
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        List<UmsAdmin> userBeanList = umsAdminService.list(wrapper);
        for (UmsAdmin userBean : userBeanList) {
            System.out.println(userBean);
        }
    }


    @Test
    void newOrg() {
        OmsOrganization organization = organizationService.getById(10L);
        System.out.println(organization.toString());
    }
}
