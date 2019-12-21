package com.alsc.net.util;

/**
 * Created by Mirko on 2017/2/14.
 * 请求方法
 *
 */

public interface ConstantUrl {


    //隐私条款链接
    String URL_PRIVACY = "http://www.wearme.cc/dabainiu/yinsi.htm";
    //服务条款链接
    String URL_SERVICE = "http://www.wearme.cc/dabainiu/yinsi.htm";

    /**
     * 请求头名称和值
     */
    String HEAD_NAME = "TD-Agent";
    String HEAD_VALUE = "en_US@currency=USD,device=ios,ad421e72d15c0c3fa230c55cd728d7fd";

    String PROMOTION_DATA = "promotion/collection";

    ///检查版本
    String CHECK_VERSION = "version/newest";

    //登录
    String LOGIN_URL = "login";
    //朝圣地图列表
    String WORSHIP_LIST_URL = "worship/get/available";
    //朝圣地图详情
    String WORSHIP_DEATIL_URL = "worship/get/placeinfo";
    //开始朝圣
    String WORSHIP_START_URL = "worship/start";
    //结束朝圣
    String WORSHIP_FINISH_URL = "worship/finish";
    //获取朝圣次数
    String GET_WORSHIP_COUNT = "worship/get/haskneel";
    //阶段完成请求
    String WORSHIP_FINISH_STAGE = "level/add/finishworshipstage";

    //获取总的跪拜记录
    String GET_KNEEL_TOTAL = "kneel/get/total";

    //获取思列表页面
    String GET_THINK_ARTICLE = "think/main/get/notres";
    //获取思详细
    String GET_THINK_ARTICLE_DETAIL = "think/main/get/detail";

    //闻 部列表页
    String GET_FIND_BOOK_LIST = "book/main/get";
    //闻 书列表页
    String GET_FIND_BOOK_CATALOG_LIST = "book/catalog/get2";
    //闻 书搜索
    String GET_FIND_BOOK_SEARCH = "ios/book/keyword/search";

    //全国排行
    String GET_RANKING_COUNTRY = "rankkneel/getall";

    //全省排行
    String GET_RANKING_PROVINCE = "rankkneel/getprovince";

    // 意见反馈
    String FEED_BACK_SUBMIT = "feedback/submit";

    // 关注的人列表
    String FOLLOW_LIST = "frienduser/get";
    // 搜索好友
    String FOLLOW_SEARCH = "frienduser/search";
    // 添加关注
    String FOLLOW_ADD = "frienduser/add";
    // 取消关注
    String FOLLOW_CANCLE = "frienduser/cancelattention";

    // 上传本地通讯录获取是否有加入PurePrac用户
    String FOLLOW_CONTACTS_UPLOAD = "frienduser/checkuser/isapp2";
    // 邀请好友
    String FOLLOW_CONTACTS_INVITED = "frienduser/invited/sendsms";
    // 好友详细信息
    String FOLLOW_PERSON_DETAIL = "frienduser/get/friendinfo";

    // 推荐的群组
    String GROUP_RECOMMEND = "friendgroup/recommend";
    // 我的群组
    String GROUP_MY_LIST = "friendgroup/get/my";
    // 申请加入群组
    String GROUP_ADD_APPLICATION = "friendgroup/application";
    // 搜索群组
    String GROUP_SEARCH = "friendgroup/search";
    // 创建群组
    String GROUP_CREAT = "friendgroup/create";
    // 获取群组成员
    String GROUP_MEMBERS = "friendgroup/get/member";
    // 踢出群组成员
    String GROUP_MEMBERS_REMOVE = "friendgroup/right/remove";
    // 设置管理员
    String GROUP_ADMIN_SETTING = "friendgroup/right/setmanager";
    // 移除管理员
    String GROUP_ADMIN_REMOVE = "friendgroup/right/revokemanager";
    // 退出群组
    String GROUP_MEMBERS_EXIT = "friendgroup/exit";
    // 解散群组
    String GROUP_DESTORY = "friendgroup/destroy";
    // 查询本人在群组里面的身份 （群主，管理员，普通）
    String GROUP_MY_IDENTITY = "friendgroup/get/myright";
    // 群组举报
    String GROUP_REPORT = "friendgroup/submit/report";
    // 群组简介信息
    String GROUP_DETAIL = "friendgroup/get/bygroupid";
    // 群组信息更新
    String GROUP_INFO_UPDATE = "friendgroup/updateinfo";
    // 群组头像更新
    String GROUP_HEAD_UPDATE = "friendgroup/updateface";
    // 群组里面好友关注列表
    String GROUP_FRIENDS_LIST= "frienduser/get/invitgroup";
    // 群组里面邀请好友
    String GROUP_INVITE= "friendgroup/invited";
    // 群排行
    String GROUP_RANKING= "rankkneel/get/group";


    // 同意加入群组的邀请
    String GROUP_INVITED_AGREE= "friendgroup/invited/agree";
    // 拒绝加入群组的邀请
    String GROUP_INVITED_REFUSE= "friendgroup/invited/refuse";
    // 同意加群
    String GROUP_ADD_AGREE= "friendgroup/application/agree";
    // 拒绝加群
    String GROUP_ADD_REFUSE= "friendgroup/application/refuse";


    // 获取积分等级
    String BADAGE_LEVEL= "level/get/kneel";

    //手动添加
    String HAND_COUNTING= "counting/add";


    //首页滚动消息
    String MSG_HOME_SCROLL = "msg/get/main";
    //获取普通消息列表
    String MSG_COMMON = "msg/get/common";
    //获取群组类型消息
    String MSG_GROUP = "msg/get/group";
    //消息已读操作
    String MSG_READED = "msg/update/readed";
    //获取群组未读消息条数
    String MSG_GROUP_UNREADED = "msg/get/unreadgroup";

    //上传CID
    String SAVE_CID = "login/save/cid";

    //当天手动计数
    String HANDLE_COUNT = "kneel/get/totalhand/day";


    /*********新修改*********/
    //获取课程总列表
    String COURSE_LIST = "counting/get/bytype";
    //获取获取我的修行课程
    String MY_COURSE_GET = "counting/get/mycourse";
    //添加课程kneel/get/hour
    String MY_COURSE_ADD = "counting/add/mycourse";
    //课程搜索
    String COURSE_SEARCH = "counting/search/course";
    //群排行 多条件获取
    String GROUP_RANKING_MULT = "rankkneel/group/multicondition";

    //群公告 -- 发布
    String GROUP_NOTICE_ADD = "friendgroup/notice/add";
    //群公告 -- 获取
    String GROUP_NOTICE_GET = "friendgroup/notice/get";
    //群公告 -- 删除
    String GROUP_NOTICE_DELETE = "friendgroup/notice/delete";

    //群活动 -- 发布
    String GROUP_ACT_ADD = "friendgroup/activity/add";
    //群活动 -- 获取
    String GROUP_ACT_GET = "friendgroup/activity/getall/orderdate";
    //群活动 -- 编辑
    String GROUP_ACT_EDIT = "friendgroup/activity/edit";
    //群活动 -- 删除
    String GROUP_ACT_DELETE = "friendgroup/activity/delete";
    //群活动 -- 报名
    String GROUP_ACT_SINGUP = "friendgroup/activity/event";
    //群活动 -- 取消报名
    String GROUP_ACT_CANCLE_SINGUP = "friendgroup/activity/cancelevent";

    //获取支付签名
    String GET_PAY_SIGN = "shop/get/paysign";
    //获取支付宝签名
    String GET_ALIPAY_SIGN = "zfb/getsign";
    //获取微信支付信息
    String GET_WX_SIGN = "wx/getorder";

    //获取设备列表
    String GET_DEVICE_LIST = "device/list";

    //上传监测数据
    String  UPLOAD_SENSOR_DATA = "version/kneel/addrawdata";

    //商城 首页Banner
    String  MALL_HOME_BANNER = "shop/get/mainbanner";
    //商城 首页商品列表
    String  MALL_HOME_SHOP_LIST = "shop/get/shoplist";
    //商城 商品详细
    String  MALL_PRODUCT_DETAIL = "shop/get/shopinfo";
    //商城 商品属性
    String  MALL_PRODUCT_OPTION = "shop/get/allshopmodel";
    //商城 地址列表
    String  MALL_ADDRESS_LIST = "shop/get/addressls";
    //商城 添加地址
    String  MALL_ADDRESS_ADD = "shop/add/address";
    //商城 获取默认地址
    String  MALL_ADDRESS_GET_DEFALUT = "shop/get/defaddress";
    //商城 设置默认地址
    String  MALL_ADDRESS_SET_DEFALUT = "shop/set/defaddress";
    //商城 删除地址
    String  MALL_ADDRESS_DEL = "shop/delete/address";
    //商城 修改地址
    String  MALL_ADDRESS_EDIT = "shop/update/address";
    //商城 提交订单
    String  MALL_ORDER_SUBMIT = "shop/add/orderdetail";
    //商城 获取优惠券列表
    String  COUPON_LIST = "shop/get/usecouponls";
    //商城  获取过期的优惠券
    String  COUPON_VAILD = "shop/get/unusecouponls";
    //商城 获取订单列表
    String  MALL_ORDER_LIST = "shop/get/orderls";
    //商城 获取订单详细
    String  MALL_ORDER_DETAIL = "shop/get/orderdetail";
    //商城  删除订单
    String  MALL_ORDER_DELETE = "shop/delete/orderdetail";
    //商城  获取我的推广码
    String  MY_EXTENSION_CODE = "marketing/add/mycode";
    //商城 支付成功
    String  PAY_SUCESS = "shop/update/paysuccess";

    //功课  手动报数
    String  COURSE_HANDLE_COUNT = "course/add/handcount";
    //功课  添加功课
    String  COURSE_ADD = "course/add/mycourse";
    //功课  删除功课
    String  COURSE_DEL = "course/detele/mycourse";
    //功课  我的功课列表
    String  COURSE_MY_LIST = "course/get/mycourse";
    //功课  我的功课列表(带修行数据)
    String  COURSE_MY_LIST_COUNT = "course/get/myandcount";
    //功课  获取功课列表（添加页面）
    String  COURSE_SEARCH_TYPE_LIST = "course/get/bycoursetype";
    //功课  搜索功课列表
    String  COURSE_SEARCH_RESULT = "course/get/bykeyword2";
    //功课  申请添加课程
    String  COURSE_ADD_CHECK = "course/add/addcheck";
    //功课  设备同步数据（上传实时数据）
    String  COURSE_SYNC_DATA = "course/addorupdate/devicedata";
    //功课  获取最后一条数据更新时间
    String  COURSE_SYNC_LAST_TIME = "course/get/bestdatedevicecounting";
    //功课  数据汇总
    String  COURSE_TOTAL_COUNT = "course/get/totalcount";
    //功课  回向
    String  COURSE_BACK = "course/add/hx";

    //历史数据  月显示数据
    String  HISTORY_DATA_MONTH = "course/get/statisticsmonth";
    //历史数据  月详细数据
    String  HISTORY_DATA_MONTH_DETAIL = "course/get/statisticsmonthdetail";
    //历史数据  年显示数据
    String  HISTORY_DATA_YEAR = "course/get/statisticsyear";
    //历史数据  年详细数据
    String  HISTORY_DATA_YEAR_DETAIL = "course/get/statisticsyeardetail";
    //历史数据  总数据显示
    String  HISTORY_DATA_ALL = "course/get/statisticstotal";
    //历史数据  总数据详细
    String  HISTORY_DATA_ALL_DETAIL = "course/get/statisticstotaldetail";
    //历史数据  修改手动计数
    String  HISTORY_DATA_EDIT = "course/update/handcount";
    //历史数据修改
    String  HISTORY_DATA_CHANGE = "course/update/history";

    //设备   设备列表
    String  DEVICE_LIST = "device/list/bycourseid";
    //设备   更新设备名称
    String  DEVICE_NAME_UPDATE = "device/update/devname";
    //设备   删除设备
    String  DEVICE_DEL = "device/detele/dev";
    //设备   戒指设备基数--增加
    String  DEVICE_RING_BASE_COUNT_ADD = "course/add/devicebasecount";
    //设备   戒指设备基数-- 获取
    String  DEVICE_RING_BASE_COUNT_GET = "course/get/devicebasecount/bydeviceid";
    //设备   检查设备是否支持课程
    String  DEVICE_CHECK_IS_COURSE = "device/gettypecode/bycourseid";

    //第三方登录---检查用户是否存在
    String  THIRD_BIND_CHECK = "user/checkthirdbind";
    //第三方登录---绑定手机
    String  THIRD_BIND_PHONE = "user/bindThird";
    //第三方登录---登录
    String  THIRD_LOGIN = "user/thirdlogin";
    //第三方登录---注册
    String  THIRD_REGISETER = "register/third";

	
	  /**********************************************IM*************************************/
    //获取accid 和 token
    String IM_CREATE_ACCID = "im/wy/create/accid";
    //通过电话号码获取accid
    String IM_USE_PHONE_GET_ACCID = "im/wy/findaccid/byphone";
    //获取指定用户的系统设置(添加好友是否需要验证)
    String IM_GET_ADD_FRIEND_IF_VERIFY = "im/wy/find/settingadverification/byaccid";
    //修改我的系统设置(添加好友是否需要验证)
    String IM_UPDATE_ADD_FRIEND_IF_VERIFY = "im/wy/update/mysetting";
    //获取好友列表
    String IM_GET_FRIENDS = "im/wy/getFriends2";



    //获取好友详细信息
    String IM_GET_USER_DATA = "friendgroup/getGroupUserInfo";
    //搜索群
    String IM_SEARCH_TEAM = "im/wy/searchGroup";
    //创建群
    String IM_CREATE_TEAM ="im/wy/addGroup2";
	
    //创建群活动--新修改
    String GROUP_ACT_CREAT_NEW = "friendgroup/activity/add2";
    //群活动列表
    String GROUP_ACT_LIST_NEW = "friendgroup/activity/getall2";
    //群活动 课程列表
    String GROUP_ACT_COURSE_LIST = "friendgroup/course/datalist";
    //群活动 课程列表不带数据
    String GROUP_ACT_COURSE_LIST_NO_DATA = "friendgroup/course/list";
    //群活动 活动编辑
    String GROUP_ACT_EDIT_NEW = "friendgroup/activity/edit2";
	
	    //群活动 活动取消
    String GROUP_ACT_CANCLE_NEW = "friendgroup/activity/cancel";
    //群活动 功课详情
    String GROUP_ACT_COURSE_DETAIL = "friendgroup/activity/detail";

    //群   功课排名
    String GROUP_COURSE_RANK = "friendgroup/course/rank2";
    //群   举报选项
    String GROUP_REPORT_LIST = "friendgroup/get/report";
    //群   举报
    String GROUP_REPORT_NEW = "friendgroup/submit/report2";
    //群   公告
    String GROUP_NOTICE_LIST_NEW = "friendgroup/notice/get2";
    //群   公告发布
    String GROUP_NOTICE_CREAT_NEW = "friendgroup/notice/add2";
    //群   课程添加
    String GROUP_COURSE_ADD = "friendgroup/course/add2";
    //群   课程删除
    String GROUP_COURSE_DELETE = "friendgroup/course/delete";
	
    //发送添加好友请求
    String IM_ADD_FRIEND_MSG = "im/wy/addFriendMsg";
    //邀请好友加入群组
    String IM_INVITATION_FRIEND_JOIN_TEAM = "friendgroup/invitedInGroup2";
    //群组或管理员同意或者拒绝用户加入群组
    String IM_AGREE_OR_REJECT_JOIN_TEAM = "friendgroup/oprateInGroup2";

    //群组或管理员同意或者拒绝用户加入群组3
    String IM_AGREE_OR_REJECT_JOIN_TEAM_THREE = "friendgroup/oprateInGroup4";

    //好友添加,同意或者拒绝
    String IM_AGREE_OR_REJECT_ADD_FRIEND = "im/wy/modifyFriendMsg";
    //被邀请用户同意或者拒绝入群
    String IM_USER_AGREE_OR_REJECT_JOIN_TEAM = "friendgroup/modifyInGroup3";
    //申请加入群组
    String IM_APPLY_FOR_TEAM = "friendgroup/applyForGroup";
    //根据昵称或电话号码搜索用户
    String IM_SEARCH_USER = "im/wy/searchUser";
    //根据userId 获取到 Accid
    String IM_USERID_CONVERT_ACCID = "im/wy/getAccId";
    //修改群基本信息
    String IM_UPDATE_TEAM_INTRODUCE = "im/wy/modifyGroup";
    //解散群
    String IM_REMOVE_TEAM = "im/wy/removegroup2";
    //获取群详细信息
    String IM_GET_TEAM_DATA = "friendgroup/get/detailinfo";
    //获取推荐的群组
    String IM_GET_RECOMMEND_TEAM = "im/wy/recommend";
    //转让群主
    String IM_CHANGE_TEAM_OWNER = "im/wy/changeOwner";

    //获取首页Banner
    String HOME_GET_BANNER = "common/get/bycomnum";
    //消息列表删除
    String MSG_DEL = "msg/detele/one";
    //获取我创建的群
    String IM_GET_MY_CREATE_TEAM = "im/wy/getMyGroup";
    //我加入的群
    String IM_GET_MY_JOIN_TEAM = "im/wy/getIntoGroup";
    //是否是好友
    String IM_IS_FRIEND = "im/wy/isFriend";
    //是否是黑名单
    String IM_IS_BLACK = "im/wy/isBlock";
    //获取个人在群组的权限
    String IM_GET_TEAM_IDENTITY = "im/wy/identityInGroup";
    //获取我的黑名单
    String IM_GET_BLACK_LIST = "im/wy/getBlockList";
    //设置个人在群组的昵称
    String IM_SET_TEAM_NICK = "im/wy/updateNick";
    //退群
    String IM_EXIT_TEAM = "im/wy/leaveGroup";
    //踢人出群
    String IM_KICK_TEAM = "im/wy/kickGroup";
    //禁言或解禁群成员
    String IM_SHUT_UP_OR_RELIEVE = "im/wy/gagGroupMembers";
    //任命或移除管理员
    String IM_SET_MANAGER_OR_REVOCATION_MANAGER = "im/wy/addGroupManager";
    //设置群消息提醒开关
    String IM_SET_TEAM_ALERT_SWITCH = "im/wy/setGroupCue";
    //获取好友个人资料操作部分
    String IM_GET_USER_DATA_OPERATE = "im/wy/getFriendInfo";
    //设置取消黑名单
    String IM_SET_OR_CANCEL_BLACK = "im/wy/setSpecialRelation";
    //删除好友
    String IM_DELETE_FRIEND = "im/wy/deleteFriend";
    //设置好友备注
    String IM_SET_FRIEND_NICK= "im/wy/modifyFriendInfo";
    //获取群信息及群成员列表信息
    String IM_GET_TEAM_DATA_AND_TEAM_MEMBER_DATA = "im/wy/getGroupMembers";
    //通过accId获取userId
    String IM_ACCID_CONVERT_USERID = "im/wy/getUserId";
    //获取所有群组tid
    String IM_GET_ALL_TEAM_ID= "im/wy/allGroupId";
    //获取所有用户Id
    String IM_GET_ALL_USER_ID = "im/wy/allUserId";
    //注册
    String IM_REGISTER = "register";
    //获取群禁言列表
    String IM_TEAM_MUTE_LIST = "im/wy/getCueAll";
    //新手第一次登录 加入新手群 添加好友
    String IM_NOVICE_LOGIN = "im/wy/comerGroup";
    //获取是否显示自己的发愿的状态
    String IM_GET_IS_SHOW_VOW = "im/wy/getHide";
    //设置是否显示自己的发愿
    String IM_SET_IS_SHOW_VOW = "im/wy/settingHide";

    String IM_SAVE_REQUEST_USER = "im/wy/saveRequestUser";
	
	  //功课首次进入 上传历史数据
    String HISTORY_COURSE_ADD = "course/add/history";
    //功课首次进入 判断有无历史数据
    String HISTORY_COURSE_JUDGE = "course/judge/historyexist";
    //设置发愿
    String SETTING_WISH = "wish/settingWishs";
    //根据课程Id获取发愿信息
    String GET_WISH_DATA = "wish/getWishs";
    //获取用户发愿记录
    String GET_WISH_RECORD = "wish/getWishsRecord2";
    //结束发愿
    String END_WISH = "wish/endWish";
    //帮助中心 url
    String HELP_CENTER_URL = "http://www.dabainiu.com/xinshoubangzhu";
    //客服 url
    String SERVICE_URL = "http://sale.dabainiu.com/html/service.html?userId={0}&nickName={1}";
    //获取是否隐藏我的功课状态
    String GET_IS_HIDE_MY_COURSE = "im/wy/getCourseHide";
    //设置是否隐藏我的功课状态
    String SET_IS_HIDE_MY_COURSE = "im/wy/settingCourseHide";
    //获取功课记录
    String GET_COURSE_RECORD = "course/mycourse/containhistory";
    //获取简易群组信息和群成员
    String GET_SIMPLE_TEAM_DATA_TEAM_MEMBER = "im/wy/getMembersList";
    //转赠设备
    String DEVICE_PRESENT = "device/present";
    //转赠状态
    String DEVICE_PRESENT_STATUS = "device/present/updatestatus";
    //获取消息分类所有消息
    String MESSAGE_CENTER_GET_MSG = "msg/normalMsg";
    //获取回向分类所有消息
    String MESSAGE_CENTER_GET_DEDICATION = "msg/backMsg";
    //获取审核分类所有消息
    String MESSAGE_CENTER_GET_AUDIT = "msg/checkMsg";
    //主页判断所有消息类型是否有未读信息
    String ALL_MSG_IS_UNREAD = "msg/readAllStatus";
    //消息中心每个分类是否有未读信息
    String MSG_CENTER_IS_UNREAD = "msg/readThreeStatus";
    //切换tab分类 将分类标记为已读
    String MSG_CENTER_SIGN_READ = "msg/signIsRead";
    //查看回向列表详细信息
    String GET_DIDECATION_DETAILED_DATA = "msg/msgBackDetails";
    //删除个人所有回向消息
    String MSG_DELETE_ALL_DEDICATION = "msg/delPersonMsg";
    //切换审核消息分类全部置为已读
    String MSG_AUDIT_ALL_READ = "msg/signChkIsRead";
    //删除消息
    String MSG_DELETE = "msg/delMsg";
    //根据条件统计群功课数据
    String TEAM_COURSE_COUNTING_STATISTICS = "friendgroup/get/coursecountingstatistics";
    //获取功课列表(非分页)
    String NEW_GET_COURSE_LIST= "course/sysCourseList2";
    //修模块获取群信息
    String HOME_GET_TEAM_DATA = "friendgroup/get/homegroup";
    //功课查看权限设置
    String COURSE_LOOK_PERMISSION_SETTING = "course/settingCourse";
    //获取功课设定权限针对的成员
    String GET_COURSE_PERMISSION_TO_FRIEND = "course/getSetCourseFriends";
    //反垃圾文字过滤
    String KEY_WORD_CHK = "course/keyWordChk";
    //新增自定义功课
    String ADD_DIY_COURSE = "course/addDIYCourse";
    //标记课程已读
    String HOME_SINGED_COURSE = "course/signCourseIsRead";

    //课程数据修改
    String COURSE_COUNT_UPDATE = "course/counting/update";

    ///群功课信息，带目标
    String GROUP_COURSE_GOAL = "friendgroup/course/goallist2";

    ///群功课统计
    String GROUP_COURSE_CONTING = "friendgroup/query/coursecountingstatistics/byuser";

    //设置群功课目标
    String GROUP_SET_COURSE_GOAL = "friendgroup/add/setcoursegoal";

    ///获取群功课(标识是否用户已添加)
    String GROUP_GET_COURSE_HADADD = "friendgroup/course/mylist2";
	
	  //群排行点赞
    String LIKE = "friendgroup/course/rankpraise";

    ///获取群成员列表
    String GROUP_MEMBER_INFO = "friendgroup/get/members";
    //群添加自定义功课
    String TEAM_ADD_DIY_COURSE = "course/addDIYGroupCourse";

    //查询发愿文、回向文模板
    String BACKWARD_TEMPLATES = "v1/merits/course/getmeritstemplet";

    ///保存发愿文、回向文模板
    String BACKWARD_TEMPLATES_SAVE = "v1/merits/course/savemeritstemplet";

    ///获取回向文设置
    String BACKWARD_GET_SETTING = "v1/merits/course/getmeritsuserdefine";

    ///获取默认回向文模板
    String BACKWARD_GET_DEFAULT = "v1/merits/course/getdefaulttemplet";

    ///保存回向设置
    String BACKWARD_SAVE_SETTING = "v1/merits/course/savemeritsuserdefine";
	
	//我收到的回向
	String MY_RECEIVE_DEDICATION = "v1/merits/personal/receivemerits";
    //我发出的回向
    String MY_SEND_DEDICATION = "v1/merits/personal/sendmerits";
	
    //新增自定义功课(提交审核)
    String AUDIT_ADD_DIY_COURSE = "course/addDIYCourse2";

    ///获取首页数据
    String HOME_DATA = "courseprac/firstmain";

    ///课程顺序
    String COURSE_ORDER = "course/update/myorder";
    //设置功课提示音
    String COURSE_SOUND_SETTING = "user/setting/countingvoice";
    //开始回向
    String COURSE_WISH_BACK_START = "v1/merits/course/savemeritstarget";
    //我的--发愿文列表
    String MY_WISH_CONTENT_LIST = "v1/merits/personal/getmeritswish";
    //获取群列表(去掉创建的加入的分类)
    String GET_TEAM_LIST = "im/wy/getGroups";
    //群总数统计主列表(每门功课的已完成总数)
    String TEAM_SUM_STATISTICS_LIST = "friendgroup/view/queryGCCCData";
    //群总数统计功课完成列表
    String TEAM_SUM_STATISTICS_COURSE_DATA_LIST = "friendgroup/view/queryGCCCDDatalist";
    //群功课统计(根据用户模糊查询)
    String BASIS_USER_SEARCH_TEAM_SUM_STATISTICS = "friendgroup/view/queryCCDTByuser";
    //共修群回向(给群成员发送回向消息)
    String SEND_TEAM_DEDICATION_MSG = "merits/course/groupMerits";

    //手机验证码获取
    String GET_VERIFICATION_CODE = "login/getVerifyCode";

    //手机验证码登录
    String VERIFICATION_CODE_LOGIN = "login/bycode";

    //用户修改密码
    String CHANGE_PASSWORD = "user/changePassword";

    //返回当前群功课目标类型
    String RETURN_TEAM_TARGET_TYPE = "friendgroup/course/groupGoalType";

    ////获取磕大头类型 和音乐列表
    String COURSE_TYPE_MUSIC_LIST = "v1/kotow/post/check/getKotowInfoByUser";

    ///获取最近一次磕大头背景音乐开闭信息
    String COURSE_TYPE_LAST_CHOOSE = "v1/kotow/post/check/getMusicLatestOperationStatusByUser";

    //更新用户拜佛类型信息
    String UPDATE_COURSE_TYPE = "v1/kotow/post/check/saveKotowInfoToDeviceByUser";

    ///添加用户拜佛类型信息
    String ADD_COURSE_TYPE = "v1/kotow/post/check/addKotowInfoByUser";

    ////开关背景音乐
    String CHANGE_COURSE_MUSIC_STATE = "v1/kotow/post/check/addMusicOpenAndCloseStatusByUser";

    ///获取拜佛篇信息列表
    String QUERY_BUDDAINFO_LIST = "v1/kotow/post/check/buddaInfo";

    ///查询拜佛信息
    String QUERT_BUDDAINFO = "v1/kotow/post/check/buddaInfoByUser";

    ////绑定设备
    String BIND_DEVICE = "v1/device/post/check/bindDevice";

    ///解绑设备
    String UNBIND_DEVICE = "v1/device/post/check/unbindDevice";

    ////查询设备是否已绑定
    String CHECK_BIND = "v1/device/post/check/device/checkDevice";

    ///查询设备与服务是否建立绑定关系
    String CHECK_SYNC_BIND = "v1/device/post/check/userDeviceBindSyncStatus";
	
	//查询设备课程
    String SEARCH_DEVICE_COURSE = "v1/device/course/post/listCourse";

    //添加设备课程
    String ADD_DEVICE_COURSE = "v1/device/course/post/addCourse";

    //设备功课排序
    String DEVICE_COURSE_RANK = "v1/device/course/post/saveCourseOrder";

    //删除设备课程
    String DELETE_DEVICE_COURSE = "v1/device/course/post/delCourse";

    //轮询
    String RECYCLE_EQUEST = "v1/kotow/post/check/getSendStatus";

    ////设置MAC地址给设备
    String SYNC_MAC_ADDRESS = "v1/kotow/post/check/sendImeiToDeviceByUser";

    ////控制拜佛音乐播放位置
    String COMMAND_COURSE_MUSIC = "v1/kotow/post/check/sendBuddaOrderToDeviceByUser";

    /**
     * 获取拜佛篇信息
     */
    String QUERY_BUDDAINFO = "v1/kotow/post/check/buddaInfoAllByUser";

    /**
     * 查询单门功课的基数
     */
    String QUERY_SINGLE_BASECOUNT = "v1/count/post/check/course/getBaseCount";

    /**
     * 查询该设备所有功课
     */
    String QUERY_MULTI_BASECOUNT = "v1/count/post/check/course/multiBaseCount";

    /**
     * 上传该设备关联的某一门功课的基数
     */
    String UPLOAD_SINGLE_BASECOUNT = "v1/count/post/check/course/baseCount";

    /**
     * 上传该设备关联的所有功课的基数
     */
    String UPLOAD_MULTI_BASECOUNT = "v1/count/post/check/course/getMultiBaseCount";
}
