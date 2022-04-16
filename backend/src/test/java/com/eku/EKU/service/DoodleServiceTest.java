package com.eku.EKU.service;

import com.eku.EKU.repository.DoodleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DoodleServiceTest {

    @Autowired
    private DoodleService doodleService;

    @Autowired
    DoodleRepository doodleRepository;

    /*@Test
    void applyDoodle() {
        DoodleForm doodleForm = new DoodleForm();
        doodleForm.setContent("테스트");
        doodleForm.setUuid("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0");

        DoodleResponse response = doodleService.applyDoodle(doodleForm);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(response.getContent()).isEqualTo(doodleForm.getContent());
        assertions.assertThat(response.getUuid()).isEqualTo(doodleForm.getUuid());
        assertions.assertAll();
    }

    @Test
    void deleteDoodle() {
        try {
            Doodle doodle = doodleRepository.findAll().stream().findFirst().orElseThrow();
            DoodleForm form = new DoodleForm();
            form.setDoodleId(doodle.getDoodleId());
            doodleService.deleteDoodle(form);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateDoodle() {
        try {
            Doodle target = doodleRepository.findAll().stream().findFirst().orElseThrow();
            DoodleForm form = new DoodleForm();
            form.setDoodleId(target.getDoodleId());
            form.setContent("바꾸기 테스트");
            doodleService.updateDoodle(form);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getRecentDoodle() {
        DoodleForm form = new DoodleForm();
        form.setUuid("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0");
        List<DoodleResponse> response = doodleService.getRecentDoodle(form);
        assertThat(response).isNotEmpty();
    }*/
}