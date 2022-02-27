package com.activeclub.fileserverminio.web.service.operate;

import com.activeclub.fileserverminio.bean.dto.FileOperationDto;
import com.activeclub.fileserverminio.bean.vo.FileCoreVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 */
public interface Operate {

    /**
     * 上传文件
     *
     * @return 文件核心信息
     */
    FileCoreVo upload(MultipartFile file,
                      FileOperationDto fileOperationDto);

    /**
     * 批量上传文件
     *
     * @return 文件核心信息
     */
    List<FileCoreVo> uploadBatch(List<MultipartFile> fileList,
                                 FileOperationDto fileOperationDto);

    /**
     * 更新文件
     *
     * @return 文件核心信息
     */
    FileCoreVo update(MultipartFile file,
                      String fileCode);

    /**
     * 批量更新文件
     *
     * @return 文件核心信息
     */
    List<FileCoreVo> updateBatch(List<MultipartFile> fileList,
                           List<String> fileCodeList);

    /**
     * 下载文件
     */
    void download(String fileCode, HttpServletResponse response);

    /**
     * 批量下载文件
     *
     * @param fileCodeList List<文件code>
     */
    void downloadBatch(List<String> fileCodeList);

    /**
     * 预览
     *
     * @param fileCode 文件code
     */
    void preview(String fileCode);

    /**
     * 删除文件:软删除
     *
     * @param fileCode 文件code
     */
    void delete(String fileCode);

    /**
     * 批量删除:软删除
     *
     * @param fileCodeList List<文件code>
     */
    void deleteBatch(List<String> fileCodeList);

    /**
     * 清理软删除数据
     */
    void clean();
}
