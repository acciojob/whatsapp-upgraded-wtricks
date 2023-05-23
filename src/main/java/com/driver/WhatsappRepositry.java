package com.driver;

import java.util.*;

public class WhatsappRepositry {
    HashMap<String, String> userMap = new HashMap<>();

    HashSet<Message> messageMap = new HashSet<>();

    HashMap<Group, List<User>> groupMap = new HashMap<>();

    HashMap<Group, List<Message>> groupMessageMap = new HashMap<>();

    int groupCount = 1;

    int messageId = 1;

    public boolean checkMobileExists(String mobile) {
        return userMap.getOrDefault(mobile, null) == null;
    }

    public void addMobileNumber(String mobile, String user) {
        userMap.put(mobile, user);
    }

    public void addGroup(List<User> users, Group group) {
        groupMessageMap.put(group, new ArrayList<>());
        groupMap.put(group, users);
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        this.groupCount = groupCount;
    }

    public int addMessage(String content) {
        Message msg = new Message(messageId, content, new Date());
        messageId += 1;
        return messageId - 1;
    }

    public List<User> getGroupIfExist(String name) {
        for(Group g: groupMap.keySet()) {
            if (g.getName().equals(name)) {
                return groupMap.get(g);
            }
        }

        return new ArrayList<>();
    }

    public List<Message> getGroupMessages(String name) {
        for(Group g: groupMessageMap.keySet()) {
            if (g.getName().equals(name)) {
                return groupMessageMap.get(g);
            }
        }

        return new ArrayList<>();
    }
}
