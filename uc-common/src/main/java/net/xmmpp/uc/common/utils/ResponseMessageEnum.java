
package net.xmmpp.uc.common.utils;



public enum ResponseMessageEnum {

    /**
     * success
     */
    OK(200, null),

    /**
     * server error
     */
    SERVICE_ERROR(500, "server error"),

    /**
     * not found page
     */
    REQUEST_ERROR(404, "request error"),

    /**
     * request parameter(s) error
     */
    BAD_REQUEST(401, "bad request"),

    FLOW_LIMITATION(403, "flow limitation"),

    /**
     * Operation rejected
     */
    OPERATION_REJECTED(402, "Operation rejected"),


    GROUP_SERVER_DB_ERROR(20000, "Server db error"),

    GROUP_NOT_FOUNT(20001, "Group {0} is not found"),
    GROUP_MEMBER_NOT_FOUND(20002, "User {0} is not in group {1}"),

    GROUP_MSG_PUSH_MODE_UNKNOWN(20003, "Unknown msg_push_mode"),
    GROUP_OWNER_CANNOT_LEAVE(20004, "Owner can not leave group"),


    GROUP_TRANSFER_NOT_ALLOWED(20005, "Group only can transfer to group member, User {0} is not group member"),

    GROUP_RECOVER_MODE(20006, "Group is recovery mode"),

    GROUP_EXCEED_LIMIT_GLOBE(20007, "Global group count exceed limit"),
    GROUP_EXCEED_LIMIT_USER_CREATE(20008, "Group count user created exceed limit"),
    GROUP_EXCEED_LIMIT__USER_JOIN(20009, "Group count user joined exceed limit"),


    GROUP_CAPACITY_EXCEED_LIMIT(20010, "Group capacity exceed limit"),

    GROUP_MEMBER_PERMISSION(20011, "This operation needs group member permission"),
    GROUP_ADMIN_PERMISSION(20012, "This operation needs group admin permission"),
    GROUP_OWNER_PERMISSION(20013, "This operation needs group owner permission"),

    GROUP_APPLICATION_EXPIRED_OR_HANDLED(20014, "Application has expired or be handled"),
    GROUP_INVITATION_EXPIRED_OR_HANDLED(20015, "Invitation has expired or be handled"),

    GROUP_KICK_TOO_MANY_TIMES(20016, "User {0} has been kicked more than 3 times"),

    GROUP_MEMBER_ALREADY(20017, "User {0} is already in group {1}"),


    USER_IN_GROUP_BLOCKED_LIST(20018, "User {0} is in group {1} blockedList"),

    GROUP_ANNOUNCEMENT_NOT_FOUND(20020, "Group {0} announcement (id = {1}) is not found"),
    GROUP_ANNOUNCEMENT_FORBIDDEN(20021, "Group {0} announcement (id = {1}) has been forbidden by system admin"),

    GROUP_SHARED_FILE_NOT_FOUND(20022, "Group shared file is not found"),
    GROUP_SHARED_FILE_OPERATE_NOT_ALLOWED(20023, "Do not have permission operate shared file"),

    QR_CODE_ILLEGAL(20024, "qr code is illegal"),
    QR_CODE_EXPIRED(20025, "qr code has expired"),

    USER_NOT_EXIST(10000, "user {0} does not exist"),
    INVALID_VERIFICATION_CODE(10001, "invalid verification code"),
    INVALID_REQUEST_PARAMETER(10002, "invalid request parameter(s)"),
    MISSING_TOKEN(10003, "missing access token"),
    USER_EXIST(10004, "user has already existed"),
    IN_ROSTER(10005, "current user is in roster"),
    IN_BLOCKED_LIST(10006, "current user is in blockedList"),
    ANSWER_FAILED(10007, "the application does not exist or has already expired, fromUserId: {0}, toUserId: {1}"),
    INVALID_TOKEN(10008, "invalid token"),
    OSS_ERROR(10009, "oss error"),
    NO_PERMISSION(10010, "no permission"),
    BINDED(10011, "open_id or user_id has already binded"),
    INVALID_CODE(10012, "invalid code"),
    INVALID_STATUS(10013, "invalid status: {0}"),


    INVALID_NOTIFIER(30000, "notifier file is invalid"),

    TENEMENT_APP_NOT_FOUND(40000, "tenement app not found"),

    // 发送消息相关错误消息
    RECEIVER_NOT_REGISTER(40001, "receiver {0} does not register"),
    RECEIVER_NOT_ONLINE(40002, "receiver {0} is not online"),
    RECEIVER_ONLY_CONTACT(40003, "receiver {0} set `only_contact` to true"),
    RECEIVERS_NOT_EXIST(40004, "receivers {0} do not exist"),
    NEED_RECEIVERS(40005, "need message receivers"),

    // 安全相关
    CHECK_REGISTER_EXCEED_MAX(50001, "check register exceeds the max allowed count: {0}"),

    // db 操作相关
    DB_ACCESS_ERROR(60000, "database access error"),
    DB_QUERY_ERROR(60001, "query database error"),
    DB_INSERT_ERROR(60002, "insert into database error"),
    DB_UPDATE_ERROR(60003, "update database error"),
    DB_DELETE_ERROR(60004, "delete from database error"),

    // Shop 操作相关
    SHOP_EXIST(10013, "shop {0} already exist"),
    SHOP_SOURCE_INVALID(70001, "invalid shop source: {0}"),

    // cluster 相关
    CLUSTER_FORWARD_ERROR(80000, "cluster forward request error"),
    CLUSTER_NOT_EXIST(80001, "cluster with id: {0} not exist");

    ResponseMessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
