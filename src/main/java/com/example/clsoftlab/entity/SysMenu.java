package com.example.clsoftlab.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "ZSYS_MENU")
public class SysMenu extends BaseEntity {

	@Id
    @Column(name = "MENU_ID", length = 20, nullable = false)
    private String id; // 예: SYS, PAY_ITEM (직접 입력값 사용)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UP_MENU_ID")
    private SysMenu parentMenu;

    @OneToMany(mappedBy = "parentMenu") // 부모 엔티티의 'parent' 필드에 매핑됨
    private List<SysMenu> children = new ArrayList<>();

    @Column(name = "MENU_NM", length = 100, nullable = false)
    private String menuName;

    @Column(name = "MENU_URL", length = 200)
    private String menuUrl;

    @Column(name = "ICON_NM", length = 50)
    private String iconName;

    @Column(name = "ZORDER", nullable = false)
    @ColumnDefault("0")
    private Integer order;

    @Column(name = "ZUSE_YN", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    private String useYn;

    @Version
    @Column(name = "OBJ_VERSION", nullable = false)
    private Integer version;
}
