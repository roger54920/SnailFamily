package com.example.ysww.snailfamily.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by me-jie on 2017/4/23.
 * 收件信息
 */

public class AddressBean extends BaseBean{

    /**
     * Act : {"isNewRecord":true,"taskId":"2c9e703d9af042409aa1a4cef6f895a2","taskName":"用户立即送","taskDefKey":"sendImmediately","procInsId":"74dba87f11004477b605b1cac5463099","procDefId":"accept_process:36:5b1375d5ed3e47b59373fdac1a0acc04","procDefKey":"accept_process","status":"todo","assignee":"cd2d0097ccd24f24badba037b3989261","vars":{"map":{"time":1494510360000,"parameterMap":{"id":"2f03529c905e4832ac0a0aba38575d31","act.procInsId":"74dba87f11004477b605b1cac5463099","time":"1494510360000","expressParcel.id":"7685a3fcf4e2461e9bb8ec42ce71fdd9","sendImmediately":"false","act.taskId":"70db5baab7494ca68c8e2e687a4e0a21"},"stationmasterId":"41c9f18bc59140068c4297f24b30389d","sendImmediately":false,"mobile":"18502340014","consumerId":"cd2d0097ccd24f24badba037b3989261"}},"todoTask":true,"finishTask":false,"durationTime":"","taskCreateDate":"2017-05-11 19:46:34"}
     * expressAccept : {"id":"2f03529c905e4832ac0a0aba38575d31","isNewRecord":false,"createDate":"2017-05-11 17:20:31","updateDate":"2017-05-11 17:43:26","expressParcel":{"id":"7685a3fcf4e2461e9bb8ec42ce71fdd9","isNewRecord":false,"createDate":"2017-05-11 17:20:31","updateDate":"2017-05-11 17:20:37","shipperCode":"ZTO","lgisticCode":"779057968432","photo":"7685a3fcf4e2461e9bb8ec42ce71fdd9.jpg"},"consumer":{"id":"cd2d0097ccd24f24badba037b3989261","isNewRecord":false,"createDate":"2017-05-09 11:39:48","updateDate":"2017-05-09 11:39:48","loginName":"18502340014","name":"侯想","mobile":"18502340014","loginIp":"127.0.0.1","loginDate":"2017-05-11 19:02:41","loginFlag":"1","roleName":"consumer","oldLoginIp":"127.0.0.1","oldLoginDate":"2017-05-11 19:02:41","roleNames":"客户"},"stationmaster":{"id":"41c9f18bc59140068c4297f24b30389d","isNewRecord":false,"loginFlag":"1","roleNames":""},"workstation":{"id":"a07a9224689f468495f0f56a5ebd280e","isNewRecord":false,"remarks":"河南-安阳-汤阴-瓦岗寨","createDate":"2017-04-28 14:19:00","updateDate":"2017-04-29 18:36:58","stationName":"瓦岗寨","provincialCode":{"id":"bee95f0b8487438bb4fcaf5a4ac3dcb3","isNewRecord":false,"name":"河南","sort":30,"ifChecked":"\u0000","parentId":"0"},"cityCode":{"id":"b59038bfd1804488b81e60158664ffa1","isNewRecord":false,"name":"安阳","sort":30,"ifChecked":"\u0000","parentId":"0"},"areaCode":{"id":"c8ac80c297ef4720840195736a334ae6","isNewRecord":false,"name":"汤阴","sort":30,"ifChecked":"\u0000","parentId":"0"},"cellCode":{"id":"8a245d30a8af47de8df8d7a4bac37357","isNewRecord":false,"name":"瓦岗寨","sort":30,"ifChecked":"\u0000","parentId":"0"},"workstationCode":"1493360340375"},"receiver":{"id":"f3247a55bdc64403becb3865db9a8b10","isNewRecord":false},"ifInWorkstation":"1","containerNO":"9999","costBePaid":0,"procInsId":"74dba87f11004477b605b1cac5463099"}
     */

    private ActBean Act;
    private ExpressAcceptBean expressAccept;
    public ActBean getAct() {
        return Act;
    }

    public void setAct(ActBean Act) {
        this.Act = Act;
    }

    public ExpressAcceptBean getExpressAccept() {
        return expressAccept;
    }

    public void setExpressAccept(ExpressAcceptBean expressAccept) {
        this.expressAccept = expressAccept;
    }

    public static class ActBean {
        /**
         * isNewRecord : true
         * taskId : 2c9e703d9af042409aa1a4cef6f895a2
         * taskName : 用户立即送
         * taskDefKey : sendImmediately
         * procInsId : 74dba87f11004477b605b1cac5463099
         * procDefId : accept_process:36:5b1375d5ed3e47b59373fdac1a0acc04
         * procDefKey : accept_process
         * status : todo
         * assignee : cd2d0097ccd24f24badba037b3989261
         * vars : {"map":{"time":1494510360000,"parameterMap":{"id":"2f03529c905e4832ac0a0aba38575d31","act.procInsId":"74dba87f11004477b605b1cac5463099","time":"1494510360000","expressParcel.id":"7685a3fcf4e2461e9bb8ec42ce71fdd9","sendImmediately":"false","act.taskId":"70db5baab7494ca68c8e2e687a4e0a21"},"stationmasterId":"41c9f18bc59140068c4297f24b30389d","sendImmediately":false,"mobile":"18502340014","consumerId":"cd2d0097ccd24f24badba037b3989261"}}
         * todoTask : true
         * finishTask : false
         * durationTime :
         * taskCreateDate : 2017-05-11 19:46:34
         */

        private boolean isNewRecord;
        private String taskId;
        private String taskName;
        private String taskDefKey;
        private String procInsId;
        private String procDefId;
        private String procDefKey;
        @SerializedName("status")
        private String statusX;
        private String assignee;
        private VarsBean vars;
        private boolean todoTask;
        private boolean finishTask;
        private String durationTime;
        private String taskCreateDate;
        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskDefKey() {
            return taskDefKey;
        }

        public void setTaskDefKey(String taskDefKey) {
            this.taskDefKey = taskDefKey;
        }

        public String getProcInsId() {
            return procInsId;
        }

        public void setProcInsId(String procInsId) {
            this.procInsId = procInsId;
        }

        public String getProcDefId() {
            return procDefId;
        }

        public void setProcDefId(String procDefId) {
            this.procDefId = procDefId;
        }

        public String getProcDefKey() {
            return procDefKey;
        }

        public void setProcDefKey(String procDefKey) {
            this.procDefKey = procDefKey;
        }

        public String getStatusX() {
            return statusX;
        }

        public void setStatusX(String statusX) {
            this.statusX = statusX;
        }

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }

        public VarsBean getVars() {
            return vars;
        }

        public void setVars(VarsBean vars) {
            this.vars = vars;
        }

        public boolean isTodoTask() {
            return todoTask;
        }

        public void setTodoTask(boolean todoTask) {
            this.todoTask = todoTask;
        }

        public boolean isFinishTask() {
            return finishTask;
        }

        public void setFinishTask(boolean finishTask) {
            this.finishTask = finishTask;
        }

        public String getDurationTime() {
            return durationTime;
        }

        public void setDurationTime(String durationTime) {
            this.durationTime = durationTime;
        }

        public String getTaskCreateDate() {
            return taskCreateDate;
        }

        public void setTaskCreateDate(String taskCreateDate) {
            this.taskCreateDate = taskCreateDate;
        }

        public static class VarsBean {
            /**
             * map : {"time":1494510360000,"parameterMap":{"id":"2f03529c905e4832ac0a0aba38575d31","act.procInsId":"74dba87f11004477b605b1cac5463099","time":"1494510360000","expressParcel.id":"7685a3fcf4e2461e9bb8ec42ce71fdd9","sendImmediately":"false","act.taskId":"70db5baab7494ca68c8e2e687a4e0a21"},"stationmasterId":"41c9f18bc59140068c4297f24b30389d","sendImmediately":false,"mobile":"18502340014","consumerId":"cd2d0097ccd24f24badba037b3989261"}
             */

            private MapBean map;

            public MapBean getMap() {
                return map;
            }

            public void setMap(MapBean map) {
                this.map = map;
            }

            public static class MapBean {
                /**
                 * time : 1494510360000
                 * parameterMap : {"id":"2f03529c905e4832ac0a0aba38575d31","act.procInsId":"74dba87f11004477b605b1cac5463099","time":"1494510360000","expressParcel.id":"7685a3fcf4e2461e9bb8ec42ce71fdd9","sendImmediately":"false","act.taskId":"70db5baab7494ca68c8e2e687a4e0a21"}
                 * stationmasterId : 41c9f18bc59140068c4297f24b30389d
                 * sendImmediately : false
                 * mobile : 18502340014
                 * consumerId : cd2d0097ccd24f24badba037b3989261
                 */

                private long time;
                private ParameterMapBean parameterMap;
                private String stationmasterId;
                private boolean sendImmediately;
                private String mobile;
                private String consumerId;

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public ParameterMapBean getParameterMap() {
                    return parameterMap;
                }

                public void setParameterMap(ParameterMapBean parameterMap) {
                    this.parameterMap = parameterMap;
                }

                public String getStationmasterId() {
                    return stationmasterId;
                }

                public void setStationmasterId(String stationmasterId) {
                    this.stationmasterId = stationmasterId;
                }

                public boolean isSendImmediately() {
                    return sendImmediately;
                }

                public void setSendImmediately(boolean sendImmediately) {
                    this.sendImmediately = sendImmediately;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getConsumerId() {
                    return consumerId;
                }

                public void setConsumerId(String consumerId) {
                    this.consumerId = consumerId;
                }

                public static class ParameterMapBean {
                    /**
                     * id : 2f03529c905e4832ac0a0aba38575d31
                     * act.procInsId : 74dba87f11004477b605b1cac5463099
                     * time : 1494510360000
                     * expressParcel.id : 7685a3fcf4e2461e9bb8ec42ce71fdd9
                     * sendImmediately : false
                     * act.taskId : 70db5baab7494ca68c8e2e687a4e0a21
                     */

                    private String id;
                    @SerializedName("act.procInsId")
                    private String _$ActProcInsId307; // FIXME check this code
                    private String time;
                    @SerializedName("expressParcel.id")
                    private String _$ExpressParcelId155; // FIXME check this code
                    private String sendImmediately;
                    @SerializedName("act.taskId")
                    private String _$ActTaskId8; // FIXME check this code

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String get_$ActProcInsId307() {
                        return _$ActProcInsId307;
                    }

                    public void set_$ActProcInsId307(String _$ActProcInsId307) {
                        this._$ActProcInsId307 = _$ActProcInsId307;
                    }

                    public String getTime() {
                        return time;
                    }

                    public void setTime(String time) {
                        this.time = time;
                    }

                    public String get_$ExpressParcelId155() {
                        return _$ExpressParcelId155;
                    }

                    public void set_$ExpressParcelId155(String _$ExpressParcelId155) {
                        this._$ExpressParcelId155 = _$ExpressParcelId155;
                    }

                    public String getSendImmediately() {
                        return sendImmediately;
                    }

                    public void setSendImmediately(String sendImmediately) {
                        this.sendImmediately = sendImmediately;
                    }

                    public String get_$ActTaskId8() {
                        return _$ActTaskId8;
                    }

                    public void set_$ActTaskId8(String _$ActTaskId8) {
                        this._$ActTaskId8 = _$ActTaskId8;
                    }
                }
            }
        }
    }

    public static class ExpressAcceptBean {
        /**
         * id : 2f03529c905e4832ac0a0aba38575d31
         * isNewRecord : false
         * createDate : 2017-05-11 17:20:31
         * updateDate : 2017-05-11 17:43:26
         * expressParcel : {"id":"7685a3fcf4e2461e9bb8ec42ce71fdd9","isNewRecord":false,"createDate":"2017-05-11 17:20:31","updateDate":"2017-05-11 17:20:37","shipperCode":"ZTO","lgisticCode":"779057968432","photo":"7685a3fcf4e2461e9bb8ec42ce71fdd9.jpg"}
         * consumer : {"id":"cd2d0097ccd24f24badba037b3989261","isNewRecord":false,"createDate":"2017-05-09 11:39:48","updateDate":"2017-05-09 11:39:48","loginName":"18502340014","name":"侯想","mobile":"18502340014","loginIp":"127.0.0.1","loginDate":"2017-05-11 19:02:41","loginFlag":"1","roleName":"consumer","oldLoginIp":"127.0.0.1","oldLoginDate":"2017-05-11 19:02:41","roleNames":"客户"}
         * stationmaster : {"id":"41c9f18bc59140068c4297f24b30389d","isNewRecord":false,"loginFlag":"1","roleNames":""}
         * workstation : {"id":"a07a9224689f468495f0f56a5ebd280e","isNewRecord":false,"remarks":"河南-安阳-汤阴-瓦岗寨","createDate":"2017-04-28 14:19:00","updateDate":"2017-04-29 18:36:58","stationName":"瓦岗寨","provincialCode":{"id":"bee95f0b8487438bb4fcaf5a4ac3dcb3","isNewRecord":false,"name":"河南","sort":30,"ifChecked":"\u0000","parentId":"0"},"cityCode":{"id":"b59038bfd1804488b81e60158664ffa1","isNewRecord":false,"name":"安阳","sort":30,"ifChecked":"\u0000","parentId":"0"},"areaCode":{"id":"c8ac80c297ef4720840195736a334ae6","isNewRecord":false,"name":"汤阴","sort":30,"ifChecked":"\u0000","parentId":"0"},"cellCode":{"id":"8a245d30a8af47de8df8d7a4bac37357","isNewRecord":false,"name":"瓦岗寨","sort":30,"ifChecked":"\u0000","parentId":"0"},"workstationCode":"1493360340375"}
         * receiver : {"id":"f3247a55bdc64403becb3865db9a8b10","isNewRecord":false}
         * ifInWorkstation : 1
         * containerNO : 9999
         * costBePaid : 0.0
         * procInsId : 74dba87f11004477b605b1cac5463099
         */

        private String id;
        private boolean isNewRecord;
        private String createDate;
        private String updateDate;
        private ExpressParcelBean expressParcel;
        private ConsumerBean consumer;
        private StationmasterBean stationmaster;
        private WorkstationBean workstation;
        private ReceiverBean receiver;
        private String ifInWorkstation;
        private String containerNO;
        private double costBePaid;
        private String procInsId;
        private String businessStatus;
        private String ifConsumerPickUp;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }


        public String getIfConsumerPickUp() {
            return ifConsumerPickUp;
        }

        public void setIfConsumerPickUp(String ifConsumerPickUp) {
            this.ifConsumerPickUp = ifConsumerPickUp;
        }


        public String getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(String businessStatus) {
            this.businessStatus = businessStatus;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public ExpressParcelBean getExpressParcel() {
            return expressParcel;
        }

        public void setExpressParcel(ExpressParcelBean expressParcel) {
            this.expressParcel = expressParcel;
        }

        public ConsumerBean getConsumer() {
            return consumer;
        }

        public void setConsumer(ConsumerBean consumer) {
            this.consumer = consumer;
        }

        public StationmasterBean getStationmaster() {
            return stationmaster;
        }

        public void setStationmaster(StationmasterBean stationmaster) {
            this.stationmaster = stationmaster;
        }

        public WorkstationBean getWorkstation() {
            return workstation;
        }

        public void setWorkstation(WorkstationBean workstation) {
            this.workstation = workstation;
        }

        public ReceiverBean getReceiver() {
            return receiver;
        }

        public void setReceiver(ReceiverBean receiver) {
            this.receiver = receiver;
        }

        public String getIfInWorkstation() {
            return ifInWorkstation;
        }

        public void setIfInWorkstation(String ifInWorkstation) {
            this.ifInWorkstation = ifInWorkstation;
        }

        public String getContainerNO() {
            return containerNO;
        }

        public void setContainerNO(String containerNO) {
            this.containerNO = containerNO;
        }

        public double getCostBePaid() {
            return costBePaid;
        }

        public void setCostBePaid(double costBePaid) {
            this.costBePaid = costBePaid;
        }

        public String getProcInsId() {
            return procInsId;
        }

        public void setProcInsId(String procInsId) {
            this.procInsId = procInsId;
        }

        public static class ExpressParcelBean {
            /**
             * id : 7685a3fcf4e2461e9bb8ec42ce71fdd9
             * isNewRecord : false
             * createDate : 2017-05-11 17:20:31
             * updateDate : 2017-05-11 17:20:37
             * shipperCode : ZTO
             * lgisticCode : 779057968432
             * photo : 7685a3fcf4e2461e9bb8ec42ce71fdd9.jpg
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private String updateDate;
            private String shipperCode;
            private String lgisticCode;
            private String photo;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getShipperCode() {
                return shipperCode;
            }

            public void setShipperCode(String shipperCode) {
                this.shipperCode = shipperCode;
            }

            public String getLgisticCode() {
                return lgisticCode;
            }

            public void setLgisticCode(String lgisticCode) {
                this.lgisticCode = lgisticCode;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }

        public static class ConsumerBean {
            /**
             * id : cd2d0097ccd24f24badba037b3989261
             * isNewRecord : false
             * createDate : 2017-05-09 11:39:48
             * updateDate : 2017-05-09 11:39:48
             * loginName : 18502340014
             * name : 侯想
             * mobile : 18502340014
             * loginIp : 127.0.0.1
             * loginDate : 2017-05-11 19:02:41
             * loginFlag : 1
             * roleName : consumer
             * oldLoginIp : 127.0.0.1
             * oldLoginDate : 2017-05-11 19:02:41
             * roleNames : 客户
             */

            private String id;
            private boolean isNewRecord;
            private String createDate;
            private String updateDate;
            private String loginName;
            private String name;
            private String mobile;
            private String loginIp;
            private String loginDate;
            private String loginFlag;
            private String roleName;
            private String oldLoginIp;
            private String oldLoginDate;
            private String roleNames;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getLoginIp() {
                return loginIp;
            }

            public void setLoginIp(String loginIp) {
                this.loginIp = loginIp;
            }

            public String getLoginDate() {
                return loginDate;
            }

            public void setLoginDate(String loginDate) {
                this.loginDate = loginDate;
            }

            public String getLoginFlag() {
                return loginFlag;
            }

            public void setLoginFlag(String loginFlag) {
                this.loginFlag = loginFlag;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

            public String getOldLoginIp() {
                return oldLoginIp;
            }

            public void setOldLoginIp(String oldLoginIp) {
                this.oldLoginIp = oldLoginIp;
            }

            public String getOldLoginDate() {
                return oldLoginDate;
            }

            public void setOldLoginDate(String oldLoginDate) {
                this.oldLoginDate = oldLoginDate;
            }

            public String getRoleNames() {
                return roleNames;
            }

            public void setRoleNames(String roleNames) {
                this.roleNames = roleNames;
            }
        }

        public static class StationmasterBean {
            /**
             * id : 41c9f18bc59140068c4297f24b30389d
             * isNewRecord : false
             * loginFlag : 1
             * roleNames :
             */

            private String id;
            private boolean isNewRecord;
            private String loginFlag;
            private String roleNames;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }

            public String getLoginFlag() {
                return loginFlag;
            }

            public void setLoginFlag(String loginFlag) {
                this.loginFlag = loginFlag;
            }

            public String getRoleNames() {
                return roleNames;
            }

            public void setRoleNames(String roleNames) {
                this.roleNames = roleNames;
            }
        }
        public static class WorkstationBean {
            /**
             * "id": "f58d7bdc52164fc8a4092b0cb0bf60e9",
             * "isNewRecord": false,
             * "remarks": "北京市,市辖区,海淀区,人民社区,1号楼,1单元,101室",
             * "createDate": "2017-06-05 13:57:17",
             * "updateDate": "2017-06-05 13:57:17",
             * "stationName": "测试蜗牛站",
             * "workstationCode": "1496642237405",
             * "provincialCode": "110000",
             * "cityCode": "110100",
             * "areaCode": "110108",
             * "cellCode": "9704ad37861a4d9f944fc18c8970b560",
             * "houseNumber": "f984fba3c54646719fa19b93d57c22dc",
             * "unit": "ffe12e3e951d469da384de5ee58a4f92",
             * "room": "b27d4e44512d4bfc900c616ada979870"
             */



            private String id;
            private boolean isNewRecord;
            private String remarks;
            private String createDate;
            private String updateDate;
            private String stationName;
            private String workstationCode;
            private String provincialCode;
            private String cityCode;
            private String areaCode;
            private String cellCode;
            private String houseNumber;
            private String unit;
            private String room;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isNewRecord() {
                return isNewRecord;
            }

            public void setNewRecord(boolean newRecord) {
                isNewRecord = newRecord;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(String updateDate) {
                this.updateDate = updateDate;
            }

            public String getStationName() {
                return stationName;
            }

            public void setStationName(String stationName) {
                this.stationName = stationName;
            }

            public String getWorkstationCode() {
                return workstationCode;
            }

            public void setWorkstationCode(String workstationCode) {
                this.workstationCode = workstationCode;
            }

            public String getProvincialCode() {
                return provincialCode;
            }

            public void setProvincialCode(String provincialCode) {
                this.provincialCode = provincialCode;
            }

            public String getCityCode() {
                return cityCode;
            }

            public void setCityCode(String cityCode) {
                this.cityCode = cityCode;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getCellCode() {
                return cellCode;
            }

            public void setCellCode(String cellCode) {
                this.cellCode = cellCode;
            }

            public String getHouseNumber() {
                return houseNumber;
            }

            public void setHouseNumber(String houseNumber) {
                this.houseNumber = houseNumber;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getRoom() {
                return room;
            }

            public void setRoom(String room) {
                this.room = room;
            }
        }

        public static class ReceiverBean {
            /**
             * id : f3247a55bdc64403becb3865db9a8b10
             * isNewRecord : false
             */

            private String id;
            private boolean isNewRecord;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIsNewRecord() {
                return isNewRecord;
            }

            public void setIsNewRecord(boolean isNewRecord) {
                this.isNewRecord = isNewRecord;
            }
        }
    }
}
