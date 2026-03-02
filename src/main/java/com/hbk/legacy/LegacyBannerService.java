package com.hbk.legacy;

import com.hbk.dto.BannerCreateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LegacyBannerService {
    List<LegacyBannerResponse> list();
    List<LegacyBannerResponse> listVisible();
    LegacyBannerResponse create(BannerCreateRequest req, MultipartFile image);
}
