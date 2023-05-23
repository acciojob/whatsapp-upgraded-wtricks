package com.driver;

import java.util.Date;
import java.util.List;

public class WhatsappService {

    WhatsappRepositry repo = new WhatsappRepositry();

    public String createUser(String name, String mobile) throws Exception {
        if (repo.checkMobileExists(mobile)) {
            throw new Exception("User already exists");
        }

        repo.addMobileNumber(mobile, name);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        // Personal Group
        if (users.size() == 2) {
            Group personalGroup = new Group(users.get(1).getName(), 1);
            repo.addGroup(users, personalGroup);
            return personalGroup;
        }

        // Group of more than two users
        Group group = new Group("Group " + repo.getGroupCount(), users.size());
        repo.addGroup(users, group);
        repo.setGroupCount(repo.getGroupCount() + 1);
        return group;
    }

    public int createMessage(String content) {
        return repo.addMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        List<User> users = repo.getGroupIfExist(group.getName());
        if (users.size() == 0) {
            throw new Exception("Group does not exist");
        }

        // check user in group
        if (!checkIfUserExist(users, sender.getMobile())) {
            throw new Exception("You are not allowed to send message");
        }

        // get all messages of that group
        List<Message> messages = repo.getGroupMessages(group.getName());
        messages.add(message);

        return messages.size();
    }

    private boolean checkIfUserExist(List<User> users, String name) {
        for(User u : users) {
            if (u.getMobile().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        List<User> users = repo.getGroupIfExist(group.getName());
        if (users.size() == 0) {
            throw new Exception("Group does not exist");
        }

        if (!users.get(0).getMobile().equals(approver.getMobile())) {
            throw new Exception("Approver does not have rights");
        }

        // check user in group
        if (!checkIfUserExist(users, user.getMobile())) {
            throw new Exception("User is not a participant");
        }

        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).getMobile().equals(user.getMobile())) {
                users.remove(i);
                break;
            }
        }

        users.add(0, user);
        return "SUCCESS";
    }

    public int removeUser(User user) {
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        return "";
    }
}
