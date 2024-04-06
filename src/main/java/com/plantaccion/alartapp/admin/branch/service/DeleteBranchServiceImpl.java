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

    @Override
    public boolean deleteBranch(String solId) {
        var branch = branchRepository.findBySolId(solId);
        if (branch.isPresent()){
            branchRepository.delete(branch.get());
            return true;
        }
        return false;
    }
}
