package com.gameexpert.chat.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.gameexpert.world.entity.World;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Entity
// TODO Lv 2: 제공된 SQL과 같은 인덱스를 선언합니다.
@Table(
        name = "chat_messages",
        indexes = {
            @Index(//인덱스로 생성, 나중에 인덱스 기능을 사용할 수 있음
                    name = "idx_chat_world_created_at",
                    columnList = "world_id, created_at"
            )
        }
    )
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "world_id", nullable = false)
    private World world;

    @Column(nullable = false, length = 16)
    private String senderNickname;

    @Column(nullable = false, length = 200)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public ChatMessage(World world, String senderNickname, String content) {
        this.world = world;
        this.senderNickname = senderNickname;
        this.content = content;
    }
}
