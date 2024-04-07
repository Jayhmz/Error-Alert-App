package com.plantaccion.alartapp.admin.branch.service;

import com.plantaccion.alartapp.common.repository.app.BranchRepository;
import com.plantaccion.alartapp.exception.NoContentException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DeleteBranchServiceImpl implements DeleteBranchService{
    private final BranchRepository branchRepository;

    public DeleteBranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Transactional
    @Override
    public boolean deleteBranch(String solId) {
        var branch = branchRepository.findBySolId(solId)
                .orElseThrow(() -> new NoContentException("Branch does not exist"));
        if (branch != null){
            branchRepository.deleteById(branch.getId());
            return true;
        }
        return false;
    }
}
