package com.guidetrack.mentorship_tracker.seeders;

import com.guidetrack.mentorship_tracker.models.Admin;
import com.guidetrack.mentorship_tracker.models.Permission;
import com.guidetrack.mentorship_tracker.models.Role;
import com.guidetrack.mentorship_tracker.repositories.AdminRepository;
import com.guidetrack.mentorship_tracker.repositories.PermissionRepository;
import com.guidetrack.mentorship_tracker.repositories.RoleRepository;
import com.guidetrack.mentorship_tracker.utils.encryption.PasswordEncrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.guidetrack.mentorship_tracker.utils.encryption.constants.SeederConstants.*;


@Service
@RequiredArgsConstructor
public class SeedService {
    private final AdminRepository adminRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public void seedDB(){
        seedPermissions();
        seedRoles();
        seedAdmin();
    }

    public void seedPermissions(){


        Permission manageMentorship = new Permission(MANAGE, "create, view, update and delete on mentorship(advisors and advisees)");
        Permission viewMentorship = new Permission(VIEW, VIEW);

//        check if permissions exist
        boolean isManageMentorshipPermissionExist = permissionRepository.existsByName("Manage mentorship");
        boolean isViewMentorshipPermissionExist = permissionRepository.existsByName("View mentorship");
        if(!isViewMentorshipPermissionExist){
            permissionRepository.save(viewMentorship);
        }
        if(!isManageMentorshipPermissionExist){
            permissionRepository.save(manageMentorship);
        }
    }

    public void seedRoles(){
        Role mentorshipManager = new Role("Mentorship manager", "Perform mentorship associated CRUD actions");

        Permission manageMentorship = permissionRepository.findByName("Manage mentorship");
        Permission viewMentorship = permissionRepository.findByName("View mentorship");


        mentorshipManager.setPermissions(List.of(manageMentorship, viewMentorship));
        boolean isManagerRoleExists = roleRepository.existsByName(MANAGER);
        if(!isManagerRoleExists){
            roleRepository.save(mentorshipManager);
        }

        Role administrator = new Role(ADMIN, "Perform all actions");
        boolean isAdministratorExists = roleRepository.existsByName("Administrator");

        if (!isAdministratorExists){
            roleRepository.save(administrator);
        }
    }



    public void seedAdmin(){
        PasswordEncrypt passwordEncrypt = new PasswordEncrypt();
        Optional<Role> administrator = roleRepository.findByName(ADMIN);
        Admin admin = new Admin("amidu", "amidu@guidetrack.com", passwordEncrypt.encodePassword("amiduguides@2023"));
        boolean isAdminExists = adminRepository.existsByEmail(admin.getEmail());
        if(!isAdminExists && administrator.isPresent()){
            admin.setRole(administrator.get());
            adminRepository.save(admin);
        }
    }
}
