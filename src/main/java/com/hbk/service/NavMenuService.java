package com.hbk.service;

import com.hbk.dto.NavMenuResponseDTO;
import com.hbk.entity.NavMenu;
import com.hbk.repository.NavMenuRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //👉 final 필드 생성자 자동 생성
@Transactional
public class NavMenuService {


    private final NavMenuRepository navMenuRepository; //di

    @Transactional(readOnly = true)
    public List<NavMenuResponseDTO> tree() { //트리전체 조회
        //1차 메뉴조회
        List<NavMenu> roots = navMenuRepository.findByParentIsNullOrderBySortOrderAscIdAsc();
        //👉 각 루트 메뉴를 재귀적으로 트리 DTO 변환
        return roots.stream().map(this::toTreeDto).collect(Collectors.toList());

    }

    //✅ 2️⃣ 메뉴 생성
    public NavMenuResponseDTO create(NavMenuResponseDTO req) {
        //이름검증
        String name = req.getName() == null ? "" : req.getName().trim();
        //👉 null 방지 + 공백 제거
        if (name.isEmpty()) throw new IllegalArgumentException("name is required");
        NavMenu parent = null;//parent 기본값 = null (루트메뉴)
        int depth = 1;//depth 기본값 1=> 1차메뉴


        //parentId가 존재한다면,조회
        if (req.getParentId() != null) {
            parent = navMenuRepository.findById(req.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("parent not found:" + req.getParentId()));
            //부모가 없으면 예외발생
            depth = parent.getDepth() + 1;
            if (depth > 3) throw new IllegalArgumentException("depth max is 3");
        }
        int sortOrder = (req.getSortOrder() != null)
                ? req.getSortOrder()
                : (parent == null
                ? navMenuRepository.maxSortOrderRoot() + 1
                : navMenuRepository.maxSortOrderByParent(parent.getId()) + 1);

        String visibleYn = (req.getVisibleYn() == null || req.getVisibleYn().isBlank())
                //visibleYn이 null이거나, 빈값이면
                ? "Y"
                : req.getVisibleYn().trim().toUpperCase();

        String path = req.getPath();
        if (path != null) {
            path = path.trim();

            if (!path.isEmpty() && !path.startsWith("/")) path = "/" + path;
        }
        NavMenu saved = navMenuRepository.save(
                NavMenu.builder()
                        .name(name).path(path).depth(depth).sortOrder(sortOrder).visibleYn(visibleYn).parent(parent).build()
        );
        return toFlatDto(saved);

    }

    public void delete(Long id) {

        NavMenu menu = navMenuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found:" + id));
        navMenuRepository.delete(menu);
    }

    private NavMenuResponseDTO toFlatDto(NavMenu n) {
        return NavMenuResponseDTO.builder()
                .id(n.getId()).name(n.getName()).path(n.getPath()).depth(n.getDepth()).sortOrder(n.getSortOrder())
                .visibleYn(n.getVisibleYn())
                .build();
    }

    private NavMenuResponseDTO toTreeDto(NavMenu n) {
        NavMenuResponseDTO dto = toFlatDto(n);
        if (n.getChildren() != null && !n.getChildren().isEmpty()) {
            dto.setChildren(n.getChildren().stream().map(this::toTreeDto).collect(
                    Collectors.toList()
            ));
        }
        return dto;
    }
}