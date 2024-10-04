package com.nagornov.scientist.config.init;

import com.nagornov.scientist.model.Science;
import com.nagornov.scientist.repository.ScienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ScienceInit implements ApplicationRunner {

    private final ScienceRepository scienceRepository;

    @Override
    public void run(ApplicationArguments args) {
        addScienceIfNotExists(new Science(1, "Архитектура"));
        addScienceIfNotExists(new Science(2, "Биологические науки"));
        addScienceIfNotExists(new Science(3, "Ветеринарные науки"));
        addScienceIfNotExists(new Science(4, "Военные науки"));
        addScienceIfNotExists(new Science(5, "Географические науки"));
        addScienceIfNotExists(new Science(6, "Геолого-минералогические науки"));
        addScienceIfNotExists(new Science(7, "Искусствоведение"));
        addScienceIfNotExists(new Science(8, "Исторические науки"));
        addScienceIfNotExists(new Science(9, "Культурология"));
        addScienceIfNotExists(new Science(10, "Медицинские науки"));
        addScienceIfNotExists(new Science(11, "Науки о Земле"));
        addScienceIfNotExists(new Science(12, "Педагогические науки"));
        addScienceIfNotExists(new Science(13, "Политология"));
        addScienceIfNotExists(new Science(14, "Психологические науки"));
        addScienceIfNotExists(new Science(15, "Сельскохозяйственные науки"));
        addScienceIfNotExists(new Science(16, "Социологические науки"));
        addScienceIfNotExists(new Science(17, "Технические науки"));
        addScienceIfNotExists(new Science(18, "Фармацевтические науки"));
        addScienceIfNotExists(new Science(19, "Физико-математические науки"));
        addScienceIfNotExists(new Science(20, "Филологические науки"));
        addScienceIfNotExists(new Science(21, "Философские науки"));
        addScienceIfNotExists(new Science(22, "Химические науки"));
        addScienceIfNotExists(new Science(23, "Экономические науки"));
        addScienceIfNotExists(new Science(24, "Юридические науки"));
    }

    private void addScienceIfNotExists(Science science) {
        scienceRepository.findScienceByName(science.getName()).orElseGet(() -> scienceRepository.save(science));
    }

}
