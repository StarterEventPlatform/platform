package com.eventplatform.factory;


import com.eventplatform.exception.utils.PasswordEncoderException;
import com.eventplatform.domain.MongoMappingConstants;
import com.eventplatform.domain.model.User;
import com.eventplatform.repository.SequenceDao;
import com.eventplatform.repository.UserDataRepository;
import com.eventplatform.util.PasswordEncoder;
import com.eventplatform.util.UtilConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;

@Scope(value = "singleton")
@Component
public class UserFactory {

    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SequenceDao sequenceDao;

    public User createUser(String name, String surname, String login, String email, String password, String role) throws PasswordEncoderException {
        User user = new User();
        user.setId(sequenceDao.getNextSequenceId(MongoMappingConstants.COLLECTION_NAME_USER));
        user.setCreationDate(new Date(System.currentTimeMillis()));
        user.setName(name);
        user.setLogin(login);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password, UtilConstants.ENCODE_MD5));
        user.setRole(role);
        userDataRepository.save(user);
        return user;
    }
}
