package com.example.demo.utils;

import com.example.demo.domain.model.*;
import com.example.demo.exception.MissingParameterException;
import org.json.JSONException;

import javax.servlet.http.HttpServletRequest;

public class InputUtils {

    public static GetPostRequest buildGetPostRequest(HttpServletRequest request) {
        String idKey = "id";

        String idInString = request.getParameter(idKey);

        try {
            Long id = idInString == null ? null : Long.valueOf(request.getParameter(idKey));

            return GetPostRequest.builder()
                    .id(id)
                    .build();
        }
        catch (NumberFormatException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static CheckNewItemRequest buildCheckNewItemRequest(HttpServletRequest request) throws MissingParameterException {
        String lastIdKey = "last_id";

        String lastIdInString = request.getParameter(lastIdKey);

        try {
            Long lastId = lastIdInString == null ? null : Long.valueOf(lastIdInString);

            return CheckNewItemRequest.builder()
                    .last_id(lastId)
                    .build();
        }
        catch (NumberFormatException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static CreateCommentRequest buildCreateCommentRequest(HttpServletRequest request) throws MissingParameterException {
        String idKey = "id";
        String commentKey = "comment";
        String indexKey = "index";
        String countKey = "count";

        String idInString = request.getParameter(idKey);
        String indexInString = request.getParameter(indexKey);
        String countInString = request.getParameter(countKey);

        try {
            Long id = idInString == null ? null : Long.valueOf(request.getParameter(idKey));
            String comment = request.getParameter(commentKey);
            Integer index = indexInString == null ? null : Integer.valueOf(indexInString);
            Integer count = countInString == null ? null : Integer.valueOf(countInString);

            return CreateCommentRequest.builder()
                    .id(id)
                    .index(index)
                    .count(count)
                    .comment(comment)
                    .build();
        }
        catch (NumberFormatException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static CreateReportRequest buildCreateReportRequest(HttpServletRequest request) throws MissingParameterException {
        String idKey = "id";
        String subjectKey = "subject";
        String detailsKey = "details";

        String idInString = request.getParameter(idKey);

        try {
            Long id = idInString == null ? null : Long.valueOf(request.getParameter(idKey));
            String subject = request.getParameter(subjectKey);
            String details = request.getParameter(detailsKey);

            return CreateReportRequest.builder()
                    .id(id)
                    .subject(subject)
                    .details(details)
                    .build();
        }
        catch (NumberFormatException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static DeletePostRequest buildDeletePostRequest(HttpServletRequest request) throws MissingParameterException {
        String idKey = "id";

        String idInString = request.getParameter(idKey);

        try {
            Long id = idInString == null ? null : Long.valueOf(request.getParameter(idKey));

            return DeletePostRequest.builder()
                    .id(id)
                    .build();
        }
        catch (NumberFormatException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static GetCommentRequest buildGetCommentRequest(HttpServletRequest request) throws MissingParameterException {
        String idKey = "id";
        String indexKey = "index";
        String countKey = "count";

        String idInString = request.getParameter(idKey);
        String indexInString = request.getParameter(indexKey);
        String countInString = request.getParameter(countKey);

        try {
            Long id = idInString == null ? null : Long.valueOf(request.getParameter(idKey));
            Integer index = indexInString == null ? null : Integer.valueOf(indexInString);
            Integer count = countInString == null ? null : Integer.valueOf(countInString);

            return GetCommentRequest.builder()
                    .id(id)
                    .index(index)
                    .count(count)
                    .build();
        }
        catch (NumberFormatException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static GetListPostsRequest buildGetListPostsRequest(HttpServletRequest request) throws MissingParameterException {
        String userIdKey = "user_id";
        String lastIdKey = "last_id";
        String indexKey = "index";
        String countKey = "count";
        String inCampaignKey = "in_campaign";
        String campaignIdKey = "campaign_id";
        String latitudeKey = "latitude";
        String longitudeKey = "longitude";

        String userIdInString = request.getParameter(userIdKey);
        String lastIdInString = request.getParameter(lastIdKey);
        String indexInString = request.getParameter(indexKey);
        String countInString = request.getParameter(countKey);
        String inCampaignInString = request.getParameter(inCampaignKey);
        String campaignIdInString = request.getParameter(campaignIdKey);
        String latitudeInString = request.getParameter(latitudeKey);
        String longitudeInString = request.getParameter(longitudeKey);

        try {
            Long userId = userIdInString == null ? null : Long.valueOf(userIdInString);
            Long lastId = lastIdInString == null ? null : Long.valueOf(lastIdInString);
            Integer index = indexInString == null ? null : Integer.valueOf(indexInString);
            Integer count = countInString == null ? null : Integer.valueOf(countInString);
            Integer inCampaign = inCampaignInString == null ? null : Integer.valueOf(inCampaignInString);
            Long campaignId = campaignIdInString == null ? null : Long.valueOf(campaignIdInString);
            String latitude = latitudeInString == null ? null : latitudeInString;
            String longitude = longitudeInString == null ? null : longitudeInString;

            return GetListPostsRequest.builder()
                    .id(userId)
                    .last_id(lastId)
                    .index(index)
                    .count(count)
                    .in_campaign(inCampaign)
                    .campaign_id(campaignId)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }
        catch (NumberFormatException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static LikePostRequest buildLikePostRequest(HttpServletRequest request) throws MissingParameterException {
        String idKey = "id";

        String idInString = request.getParameter(idKey);

        try {
            Long id = idInString == null ? null : Long.valueOf(request.getParameter(idKey));

            return LikePostRequest.builder()
                    .id(id)
                    .build();
        }
        catch (NumberFormatException e) {
            throw new JSONException(e.getMessage());
        }
    }

    public static SignInRequest buildSignInRequest(HttpServletRequest request) throws MissingParameterException {
        String phoneNumberKey = "phonenumber";
        String passwordKey = "password";
        String uuidKey = "uuid";

        String phoneNumber = request.getParameter(phoneNumberKey);
        String password = request.getParameter(passwordKey);
        String uuid = request.getParameter(uuidKey);

        return SignInRequest.builder()
                .phoneNumber(phoneNumber)
                .password(password)
                .uuid(uuid)
                .build();
    }

    public static SignUpRequest buildSignUpRequest(HttpServletRequest request) throws MissingParameterException {
        String phoneNumberKey = "phonenumber";
        String passwordKey = "password";
        String uuidKey = "uuid";

        String phoneNumber = request.getParameter(phoneNumberKey);
        String password = request.getParameter(passwordKey);
        String uuid = request.getParameter(uuidKey);

        return SignUpRequest.builder()
                .phoneNumber(phoneNumber)
                .password(password)
                .uuid(uuid)
                .build();
    }

}
