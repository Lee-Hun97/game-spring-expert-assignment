package com.gameexpert.ws.handler;

import java.net.http.WebSocket;
import java.util.List;
import com.gameexpert.ws.NicknameHandshakeInterceptor;
import com.gameexpert.ws.WorldBroadcaster;
import com.gameexpert.ws.WorldSessionRegistry;
import com.gameexpert.ws.WsMessageContext;
import com.gameexpert.ws.dto.OnlineUsersResponse;
import com.gameexpert.ws.dto.WsMessages;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.tomcat.jni.SSLContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.JsonNode;

@Component
@RequiredArgsConstructor
public class OnlineUsersWsHandler implements WsMessageHandler {
    private final WorldSessionRegistry registry;
    private final WorldBroadcaster broadcaster;

    @Override
    public String type() {
        return "onlineUsers";
    }

    @Override
    public void handle(WsMessageContext context, JsonNode message) {
        // TODO Lv 15: 현재 월드의 열린 연결에서 닉네임을 조회하고 요청자에게 응답합니다.
        List<String> users = registry.entries(context.worldId())
                .stream()
                .map(entry -> entry.session())
                .filter(session -> session.isOpen())
                .map(session -> session.getAttributes().get(NicknameHandshakeInterceptor.ATTR_NICKNAME))
                .map(String::valueOf)
                .sorted()
                .toList();

        //int count = users.size();

        OnlineUsersResponse response = new OnlineUsersResponse(users);
        broadcaster.sendTo(context.session(), response);
    }
}
