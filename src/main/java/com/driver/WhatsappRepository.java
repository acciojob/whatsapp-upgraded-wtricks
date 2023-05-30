package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.

    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    //i create
    private HashMap<String,User> userMap;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
        this.userMap=new HashMap<String,User>();         //I created this line
    }



    //Solution here

    public String createUser(String name,String mobile){
        if(userMobile.contains(mobile)){
            return "User already exists";
        }
        User user = new User(name,mobile);
        userMap.put(mobile,user);
        userMobile.add(mobile);

        return "Success";
    }

    public Group createGroup(List<User> users){
        Group group = new Group();

        if(users.size()==2){
            group.setName(users.get(1).getName());
            group.setNumberOfParticipants(2);
        }
        else{
            customGroupCount++;
            group.setName("Group " + customGroupCount);
            group.setNumberOfParticipants(users.size());
        }

        groupUserMap.put(group,users);
        adminMap.put(group,users.get(0));

        return group;
    }

    public int createMessage(String content){
        Message message = new Message();
        messageId = messageId+1;
        message.setId(messageId);
        message.setContent(content);
        Date date = new Date();
        message.setTimestamp(date);

        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) {
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.

        if(!groupUserMap.containsKey(group)){
            return -1;
        }

        List<User> userList = groupUserMap.get(group);

        if(!userList.contains(sender)){
            return -2;
        }

        senderMap.put(message,sender);
        if(groupMessageMap.isEmpty()){
            groupMessageMap.put(group,new ArrayList<>());
        }

        List<Message> messageList = groupMessageMap.get(group);
        messageList.add(message);

        return messageList.size();

    }

    public String changeAdmin(User approver, User user, Group group) {
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        if(!groupUserMap.containsKey(group)){
            return "Group does not exist";
        }

        List<User> userList = groupUserMap.get(group);
        if(!userList.contains(approver)){
            return "Approver not present in group";
        }

        if(adminMap.get(group)!=approver){
            return "Approver does not have rights";
        }

        if(!userList.contains(user)){
            return "User is not a participant";
        }

        adminMap.put(group,user);

        return "SUCCESS";

    }


}
