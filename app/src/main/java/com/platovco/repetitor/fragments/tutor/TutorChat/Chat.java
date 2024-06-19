package com.platovco.repetitor.fragments.tutor.TutorChat;

import java.util.List;
import com.platovco.repetitor.fragments.tutor.TutorChat.Users;

public class Chat {
    private List<Message> messages;
    private Users users;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}

class Users {
    private long idStudent;
    private long idTutor;

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public long getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(long idTutor) {
        this.idTutor = idTutor;
    }
}

