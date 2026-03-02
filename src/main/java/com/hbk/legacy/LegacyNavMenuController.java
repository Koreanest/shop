package com.hbk.legacy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nav-menus")
public class LegacyNavMenuController {

    private final LegacyNavMenuService legacyNavMenuService;

    @GetMapping("/tree")
    public ResponseEntity<List<LegacyNavMenuResponseDTO>> tree(){
        return ResponseEntity.ok(legacyNavMenuService.tree());
    }

    @PostMapping
    public ResponseEntity<LegacyNavMenuResponseDTO> create(@RequestBody LegacyNavMenuResponseDTO req){
        return ResponseEntity.ok(legacyNavMenuService.create(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        legacyNavMenuService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
