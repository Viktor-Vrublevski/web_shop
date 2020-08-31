package com.web.store.service;

import com.web.store.entity.goods.Folders;
import com.web.store.entity.goods.Paper;
import com.web.store.repository.FolderRepository;
import com.web.store.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoldersService {
    private FolderRepository folderRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setProductRepository(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public void save(Folders folders) {
        folderRepository.save(folders);
    }

    public List<Folders> getAllProducts(){
        return  folderRepository.findAll();
    }

    public Folders findProduct(String name) {
        return folderRepository.findByName(name);
    }

    public void addProduct(Folders folders) {
        folderRepository.save(folders);
    }

    public void delete(int id) {
        folderRepository.deleteById(id);
    }

    public void update(Folders folder) {
        String SQL = "UPDATE folders SET folder_name=? , price=?, quantity=?, description=?, url_image=? WHERE id=?";
        jdbcTemplate.update(SQL, folder.getName(), folder.getPrice(), folder.getQuantity(),
                folder.getDescription(),folder.getUrl(), folder.getId());
    }

    public Folders findById(int id){
        return folderRepository.findById(id);
    }
}
