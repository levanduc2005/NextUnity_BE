package project.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.course.dto.module.request.CreateModuleRequest;
import project.course.dto.module.request.UpdateModuleRequest;
import project.course.dto.module.response.ModuleResponse;
import project.course.entity.Course;
import project.course.entity.Module;
import project.course.mapper.ModuleMapper;
import project.course.repository.CourseRepository;
import project.course.repository.ModuleRepository;
import project.course.service.ModuleService;
import project.shared.error.ErrorCode;
import project.shared.exception.AppException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;

    private final ModuleMapper moduleMapper;

    @Override
    @Transactional
    public ModuleResponse createModule(UUID courseId, CreateModuleRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new AppException(ErrorCode.COURSE_NOT_FOUND));

        Module module = moduleMapper.toEntity(request);
        module.setCourse(course);

        return moduleMapper.toResponse(moduleRepository.save(module));
    }

    @Override
    @Transactional
    public ModuleResponse updateModule(UUID moduleId, UpdateModuleRequest request) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_FOUND));

        moduleMapper.updateEntity(request, module);

        return moduleMapper.toResponse(moduleRepository.save(module));
    }

    @Override
    @Transactional
    public void deleteModule(UUID moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new AppException(ErrorCode.MODULE_NOT_FOUND));

        Course course = module.getCourse();
        if (course != null) {
            int lessonsCount = module.getLessons() != null ? module.getLessons().size() : 0;
            if (lessonsCount > 0) {
                int currentTotal = course.getTotalLessons() != null ? course.getTotalLessons() : 0;
                course.setTotalLessons(Math.max(0, currentTotal - lessonsCount));
                courseRepository.save(course);
            }
        }

        moduleRepository.delete(module);
    }
}
