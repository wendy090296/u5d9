package wendydeluca.u5d9.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wendydeluca.u5d9.entities.Device;
import wendydeluca.u5d9.entities.User;
import wendydeluca.u5d9.exceptions.BadRequestException;
import wendydeluca.u5d9.exceptions.NotFoundException;
import wendydeluca.u5d9.payloads.UserDTO;
import wendydeluca.u5d9.repositories.UserDAO;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    public Cloudinary cloudinaryUploader;



    public Page<User> getAllUsers(int page, int size, String sortBy) { //GET ALL CON PAGINAZIONE
        if (size < 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userDAO.findAll(pageable);
    }



    public User getUserById(UUID userId) {
        return userDAO.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User saveUser(UserDTO body) {
        // 1. Se l'email dello user non é presente,
        if (!userDAO.existsByEmail(body.email())) {
            // 2. creo un nuovo oggetto User "modellato" sul body
            User newUser = new User(body.name(), body.surname(), body.username(), body.email());
            return userDAO.save(newUser);
            // Se é già presente, lancio eccezione :
        } else throw new BadRequestException("User with email '" + body.email() + "  already exists.");



    }


    public User updateUser(UUID userId, UserDTO body) {
        User found = this.getUserById(userId);
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setUsername(body.username());
        found.setEmail(body.email());
        userDAO.save(found);
        return found;

    }

    public void deleteUser(UUID userId){
        User found = this.getUserById(userId);
        userDAO.delete(found);
}

//public void addDevice(UUID userId, Device device){
//        User user = userDAO.findById(userId).orElseThrow(()-> new NotFoundException(userId));
//        List<Device> list = user.getDevices();
//        list.add(device);
//        user.setDevices(list);
//        userDAO.save(user);
//}

    public String uploadImage(MultipartFile image) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        return url;


    }





}
