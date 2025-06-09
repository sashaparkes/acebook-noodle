package com.makersacademy.acebook.controller;

import com.makersacademy.acebook.model.User;
import com.makersacademy.acebook.repository.UserRepository;
import com.makersacademy.acebook.service.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@RestController
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    // Inserts "uploads/user_profile" filepath (taken from application.properties) as uploadDir variable value
    @Value("${file.upload-dir.user-profile}")
    private String uploadDir;

    @GetMapping("/users/after-login")
    public RedirectView afterLogin() {

        // Creates principal variable of type Default0idcUser - Spring Security class which represents a user authenticated by Auth0
        // The get commands work together to extract user attributes from Auth0 (email, given_name, family_name)
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // Uses the principal variable above to extract email, given_name and family_name from Auth0
        // Sets a default profile picture
        String username = (String) principal.getAttributes().get("email");
        String first_name = (String) principal.getAttributes().get("given_name");
        String last_name = (String) principal.getAttributes().get("family_name");
        String profile_pic = "/images/profile/default.jpg";

        // Uses the email address captured above to find the relevant user
        // OR creates a new user if doesn't exist, utilising the elements captured above
        userRepository
                .findUserByUsername(username)
                .orElseGet(() -> userRepository.save(new User(username, first_name, last_name, profile_pic)));

        return new RedirectView("/posts");
    }

    @GetMapping("/settings")
    public ModelAndView settings() {

        // Creates principal variable of type Default0idcUser - Spring Security class which represents a user authenticated by Auth0
        // The get commands work together to extract user attributes from Auth0 (email, given_name, family_name)
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // Uses the principal variable above to extract email
        // Then utilizes the 'username' variable to search the database and return matching User object
        // Creates a new ModelandView and adds the User object to be referenced in Thymeleaf
        String username = (String) principal.getAttributes().get("email");
        User userByEmail = userRepository.findUserByUsername(username).get();
        Optional<User> user = userRepository.findById(userByEmail.getId());
        ModelAndView settings = new ModelAndView("/users/settings");
        settings.addObject("user", userByEmail);
        return settings;
    }

    @PostMapping("/settings")
    public RedirectView update(@ModelAttribute User userFromForm, @RequestParam("file") MultipartFile file) throws IOException {

        // Creates principal variable of type Default0idcUser - Spring Security class which represents a user authenticated by Auth0
        // The get commands work together to extract user attributes from Auth0 (email, given_name, family_name)
        DefaultOidcUser principal = (DefaultOidcUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // Uses the principal variable above to extract email
        String email = (String) principal.getAttributes().get("email");

        // Creates a new User object representing the User extracted from the database, with matching email(username)
        // Error mapping TBC
        User userInDb = userRepository.findUserByUsername(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String profilePic = imageStorageService.storeProfileImage(file, String.valueOf(userInDb.getId()));
        if (profilePic != null) {
            userInDb.setProfilePic(profilePic);
        }

        // Sets database user properties as values returned by User object from form
        // The userFromForm is taken as an input to the function as a whole, generated by the form within the HTML template
        userInDb.setFirstName(userFromForm.getFirstName());
        userInDb.setLastName(userFromForm.getLastName());

        // Save function will update, rather than create, if user already in existence - once userInDb attributes are
        // updated, essentially overwrites existing name and image fields
        userRepository.save(userInDb);

        return new RedirectView("/settings");
    }
}
//
//    // Function to handle saving an uploaded image - takes a file and a userId string (for unique saving) as inputs)
//    private String saveImage(MultipartFile file, String userId) throws IOException {
//
//        // Creates new uploadPath item referencing the uploadDirectory object at top of the page
//        // This is configured in application.properties
//        // If it doesn't exist - create it
//        Path uploadPath = Paths.get(uploadDir);
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        // Checks that the content type of the file is of type JPEG or PNG
//        // else - Error handling TBC
//        String contentType = file.getContentType();
//        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
//            throw new IllegalArgumentException("Only JPEG or PNG images are allowed");
//        }
//
//        /*
//         Compiles elements for the filename
//         Uses original filename, and calls getFileExtension function below to extract .jpg / .png
//         Then concatenates that with the userId - so when user #1 changes prof pic - it will always save as 1.png/.jpg
//         This then links with the uploadDir variable above - so it creates a complete file path, within the correct
//         directory, and with our chosen filename
//         Bottom line replaces existing filepath - so overwrites user's existing prof_pic with a new one
//        */
//        String fileName = file.getOriginalFilename();
//        String extension = getFileExtension(fileName);
//        String newFileName = userId + extension;
//        Path filePath = uploadPath.resolve(newFileName);
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        return newFileName;
//    }
//
//    // Function to extract file extension e.g. .jpeg / .png - takes a filename as an input
//        public static String getFileExtension(String fileName) {
//
//            // Allows the item to be null
//            // Filters the string for instances of '.'
//            // Creates a substring from the last instance of '.' onwards - this isolates the extension
//            // else returns nothing (?)
//            return Optional.ofNullable(fileName)
//                    .filter(f -> f.contains("."))
//                    .map(f -> f.substring(f.lastIndexOf(".")))
//                    .orElse("");
//        }
//
//
//}
