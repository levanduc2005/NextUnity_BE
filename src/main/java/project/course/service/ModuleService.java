package project.course.service;

import project.course.dto.module.request.CreateModuleRequest;
import project.course.dto.module.request.UpdateModuleRequest;
import project.course.dto.module.response.ModuleResponse;

import java.util.UUID;

public interface ModuleService {

    ModuleResponse createModule(UUID courseId, CreateModuleRequest request);

    ModuleResponse updateModule(UUID moduleId, UpdateModuleRequest request);

    void deleteModule(UUID moduleId);
}
