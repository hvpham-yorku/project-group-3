package com.yupathbuilder.backend;

import com.yupathbuilder.backend.repo.CourseRepository;
import com.yupathbuilder.backend.repo.TermPlanRepository;
import com.yupathbuilder.backend.service.ConflictDetector;
import com.yupathbuilder.backend.service.CourseSearchService;
import com.yupathbuilder.backend.service.TermPlanService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Utility / configuration helper.
 */

@Configuration
public class AppConfig {

    @Bean
    public ConflictDetector conflictDetector() {
        return new ConflictDetector();
    }

    @Bean
    public CourseSearchService courseSearchService(CourseRepository courseRepository) {
        return new CourseSearchService(courseRepository);
    }

    @Bean
    public TermPlanService termPlanService(TermPlanRepository termPlanRepository, ConflictDetector conflictDetector) {
        return new TermPlanService(termPlanRepository, conflictDetector);
    }
}
