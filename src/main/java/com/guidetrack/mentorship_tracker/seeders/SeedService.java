package com.guidetrack.mentorship_tracker.seeders;

import com.guidetrack.mentorship_tracker.dto.requests.AdminSignupRequest;
import com.guidetrack.mentorship_tracker.dto.requests.permission.PermissionRequest;
import com.guidetrack.mentorship_tracker.dto.requests.role.RoleRequest;
import com.guidetrack.mentorship_tracker.models.Admin;
import com.guidetrack.mentorship_tracker.models.Permission;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.repositories.AdminRepository;
import com.guidetrack.mentorship_tracker.repositories.PermissionRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.services.BasicUserService;
import com.guidetrack.mentorship_tracker.services.PermissionService;
import com.guidetrack.mentorship_tracker.services.RoleService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.guidetrack.mentorship_tracker.utils.constants.SeederConstants.*;


@Service
@RequiredArgsConstructor
public class SeedService {
    private final AdminRepository adminRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final PermissionService permissionService;
    private  final RoleService roleService;
    @Qualifier("adminUserServiceImpl")
    private final BasicUserService adminService;

    public void seedDB(){
        seedPermissions();
        seedRoles();
        seedAdmin();
    }

    public void seedPermissions(){
        PermissionRequest manageMentorship = new PermissionRequest(MANAGE, "create, view, update and delete on mentorship(advisors and advisees");
        PermissionRequest viewMentorship = new PermissionRequest(VIEW, VIEW);

       permissionService.create(manageMentorship);
       permissionService.create(viewMentorship);
    }

    public void seedRoles(){
        RoleRequest mentorshipManager = new RoleRequest(MANAGER, "Perform mentorship associated CRUD actions");

        Optional<Permission> manageMentorship = permissionRepository.findByNameIgnoreCase("Manage mentorship");
        Optional<Permission> viewMentorship = permissionRepository.findByNameIgnoreCase("View mentorship");
        boolean isMentorshipManagerExists = roleRepository.existsByNameIgnoreCase("Mentorship manager");
        if (!isMentorshipManagerExists) {
            roleService.create(mentorshipManager);
        }
        Optional<Role> roleOptional = roleRepository.findByNameIgnoreCase(MANAGER);
        if (roleOptional.isPresent() && (manageMentorship.isPresent() && viewMentorship.isPresent())){
            roleOptional.get().setPermissions(Set.of(manageMentorship.get(), viewMentorship.get()));
        }


        RoleRequest administrator = new RoleRequest(ADMIN, "Perform all actions");
        boolean isAdministratorExists = roleRepository.existsByNameIgnoreCase("Administrator");

        if (!isAdministratorExists){
            roleService.create(administrator);
        }
    }


    Dotenv dotenv = Dotenv.load();
    public void seedAdmin(){
        String email = dotenv.get("ADMIN_EMAIL");
        AdminSignupRequest admin = new AdminSignupRequest(dotenv.get("ADMIN_USERNAME"), dotenv.get("FIRST_NAME"), email, dotenv.get("ADMIN_PASSWORD"));
        boolean isAdminExists = adminRepository.existsByEmail(email);
        if(!isAdminExists){
            adminService.register(admin);
        }
        Optional<Admin> admin1 = adminRepository.findByEmail(email);
        admin1.ifPresent(value -> value.setVerified(true));
        admin1.ifPresent(adminRepository::save);
    }
}
