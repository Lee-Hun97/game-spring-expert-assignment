package com.gameexpert.ws.handler;

import com.gameexpert.engine.PlayerAction;
import com.gameexpert.engine.WorldEngineManager;
import com.gameexpert.player.entity.Player;
import com.gameexpert.ws.WsMessageContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

@Component
@RequiredArgsConstructor
public class MoveWsHandler implements WsMessageHandler {
    private final WorldEngineManager engineManager;

    @Override
    public String type() {
        return "move";
    }

    @Override
    public void handle(WsMessageContext context, JsonNode message) {
        String finalSceneActionId = WsFields.optionalFinalSceneActionId(message);
        double finalSceneActionX = WsFields.finiteNumber(message, "x");
        double finalSceneActionY = WsFields.finiteNumber(message, "y");
        double finalSceneActionZ = WsFields.finiteNumber(message, "z");
        float finalSceneActionYaw = WsFields.finiteFloat(message, "yaw");
        float finalSceneActionPitch = WsFields.finiteFloat(message, "pitch");
        boolean finalSceneActionCrouching = WsFields.booleanValue(message, "crouching");
        boolean finalSceneActionGliding = WsFields.booleanValue(message, "gliding");
        // TODO Lv 12: 명세의 이동 값을 읽어 현재 사용자의 이동 요청을 엔진에 전달합니다.

        PlayerAction.Move playerActionMove = new PlayerAction.Move(
                context.nickname(),
                finalSceneActionX,
                finalSceneActionY,
                finalSceneActionZ,
                finalSceneActionYaw,
                finalSceneActionPitch,
                finalSceneActionCrouching,
                finalSceneActionGliding,
                finalSceneActionId
        );

        engineManager.enqueue(context.worldId(), playerActionMove);
    }
}
