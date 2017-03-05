package com.example.Service;

import com.example.DAO.InformationDAO;
import com.example.Model.Information;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Jassy on 2017/2/28.
 * description:
 */
@Service
public class InformationService {

    @Autowired
    InformationDAO informationDAO;

    public int releaseInformation(Information information) {
        return informationDAO.addInformation(information) > 0 ? information.getId() : 0;
    }

    public Information selectById(int id) {
        return informationDAO.getInformationById(id);
    }

    public List<Information> selectLatestInformations(int userId, int offset, int limit) {
        return informationDAO.getLatestInformations(userId, offset, limit);
    }

    public void updateTitle(String title, int id) {
        informationDAO.updateTitle(title, id);
    }

    public void updateContent(String content, int id) {
        informationDAO.updateContent(content, id);
    }

    public void updateImage(String image, int id) {
        informationDAO.updateImage(image, id);
    }

    public void updateAgreementNum(int agreementNum, int id) {
        informationDAO.updateAgreementNum(agreementNum, id);
    }

    public void updateCommentNum(int commentNum, int id) {
        informationDAO.updateCommentNum(commentNum, id);
    }
}
