package com.ligeit.ec.JobService.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Async;

/**
 * job 接口
 *
 * @author ywx
 * @date 2019/6/18
 */
public interface Job {


        public final static String JOB_METHOD_NAME = "job_method_name";

        public final static String JOB_METHOD_PARAMS = "job_method_params";

        public final static String ES_INDEX_JOB = "es_index_job";

        public final static String AIKUCUN_SYNC_JOB = "aikucun_sync_job";

        public final static String BDC_ORDER_EXPORT_JOB = "bdc_order_export_job";

        public final static String CATEGORY_PRODUCTS_EXPORT_JOB = "category_products_export_job";

        public final static String ORDERS_EXPORT_JOB = "orders_export_job";

        public final static String PRODUCT_FINANCE_EXPORT_JOB = "product_finance_export_job";

        public final static String PRODUCT_PROVIDER_EXPORT_JOB = "product_provider_export_job";

        public final static String PROVIDER_SALEORDER_EXPORT_JOB = "provider_saleorder_export_job";

        public final static String PROVIDER_SALES_EXPORT_JOB = "provider_sales_export_job";

        public final static String RECHARGE_ORDERS_EXPORT_JOB = "recharge_orders_export_job";

        public final static String SERVICE_ORDERS_EXPORT_JOB = "service_orders_export_job";

        public final static String WALLET_ORDERS_EXPORT_JOB = "wallet_orders_export_job";

        public final static String ORDERS_JOB = "orders_job";

        public final static String ACTIVEMEMBER_STATISTIC_TIMING_JOB = "activemember_statistic_timing_job";

        public final static String AIKUCUN_REQUEST_TIMING_JOB = "aikucun_request_timing_job";

        public final static String AIKUCUN_SYNC_INCREMENT_TIMING_JOB = "aikucun_sync_increment_timing_job";

        public final static String BDCORDER_SUCCESS_TIMING_JOB = "bdcorder_success_timing_job";

        public final static String CHECKORDER_SUCCESS_TIMING_JOB = "checkorder_success_timing_job";

        public final static String INTEGRAL_DAILY_TIMING_JOB = "integral_daily_timing_job";

        public final static String MEMBER_COUNTER_TIMING_JOB = "member_counter_timing_job";

        public final static String MEMBER_DAILY_TIMING_JOB = "member_daily_timing_job";

        public final static String MEMBER_GRADE_TIMING_JOB = "member_grade_timing_job";

        public final static String WALLET_DAILY_TIMING_JOB = "wallet_daily_timing_job";

        public final static String PROVIDER_SALEORDER_TIMING_JOB = "provider_saleorder_timing_job";

        public final static String START_FEE_DELAY_JOB = "start_fee_delay_job";

        public final static String START_QUERY_DELAY_JOB = "start_query_delay_job";

        public final static String START_WXREFUND_DAILY_TIMING_JOB = "start_wxrefund_daily_timing_job";

        public final static String START_SUCCESS_DAILY_TIMING_JOB = "start_success_daily_timing_job";

        public final static String START_ORDER_CANCEL_DAILY_TIMING_JOB = "start_order_cancel_daily_timing_job";

        public final static String START_UPGRADE_DAILY_TIMING_JOB = "start_upgrade_daily_timing_job";

        public final static String START_ORDER_TIMING_JOB = "start_order_timing_job";

        public final static String START_USER_ANSYN_JOB = "start_user_ansyn_job";

        public final static String START_PRODUCT_DELAYED_JOB = "start_product_delayed_job";

        @Async
        public void execute(JSONObject params);
}
