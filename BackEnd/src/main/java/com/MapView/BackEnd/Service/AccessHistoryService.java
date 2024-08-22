package com.MapView.BackEnd.Service;


import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryCreateDTO;
import com.MapView.BackEnd.dtos.AccessHistory.AccessHistoryDetailsDTO;

import java.util.List;

public interface AccessHistoryService {
    AccessHistoryDetailsDTO getAccessHistory(Long id_history);
    List<AccessHistoryDetailsDTO> getAllAccessHistory();
    AccessHistoryDetailsDTO createAccessHistory(AccessHistoryCreateDTO dados);
    AccessHistoryDetailsDTO updateAccessHistory(Long id_history);
}
