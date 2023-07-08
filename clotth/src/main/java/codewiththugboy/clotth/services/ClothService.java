package codewiththugboy.clotth.services;

import codewiththugboy.clotth.dto.DeleteCloth;
import codewiththugboy.clotth.dto.PostCloth;
import codewiththugboy.clotth.dto.UpdateCloth;
import codewiththugboy.clotth.dto.request.DeleteRequest;
import codewiththugboy.clotth.dto.request.PostRequest;
import codewiththugboy.clotth.dto.request.UpdateRequest;

public interface ClothService {
    PostCloth post(PostRequest request);
    DeleteCloth delete(DeleteRequest request);
    UpdateCloth update (UpdateRequest request);


}
