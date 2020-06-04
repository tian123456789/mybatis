package com.tgr.springbootmybatis.file.export.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgr.springbootmybatis.controller.ResponseResult;
import com.tgr.springbootmybatis.file.util.PageVo;
import com.tgr.springbootmybatis.file.util.Paging;
import com.tgr.springbootmybatis.file.util.SearchFilter;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Service
public class DataService {

    private static Logger logger = LoggerFactory.getLogger(DataService.class);

    /*@Autowired
    private EntityManager entityManager;*/

    private static List<String> tableNames = null;

    public void init() {
        getAllTables("");
    }

    /**
     * 清空表名数组
     */
    public void cleanTableNames() {
        tableNames = null;
    }

    /**
     * 获取数据库用户下所有表名
     *
     * @param db_name
     * @return
     */
    public List getAllTables(String db_name) {
        if (tableNames == null) {
            String sql = "select a.table_name as `name`,ifnull(a.table_comment,a.table_name) as `comment` from information_schema.TABLES a where TABLE_SCHEMA= '" + db_name + "'";
            if (StringUtils.isBlank(db_name)) {
                sql = "select a.table_name as `name`,ifnull(a.table_comment,a.table_name) as `comment` from information_schema.TABLES a ";
            }
           /* Query query = entityManager.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
            tableNames = query.getResultList();*/
        }
        return tableNames;
    }

    /**
     * 查询表内数据
     *
     * @param tableName  表名
     * @param conditions 查询条件
     * @param limit      是否限制条数 ,默认限制 1000条
     * @return
     */
    public List getDbDataList(String tableName, Collection<SearchFilter> conditions, boolean limit) {
        String sql = buildQuerySql(tableName, conditions);
        if (limit) {
            sql += " limit 1000";
        }
        /*Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();*/
        //return query.getResultList();
        return null;
    }

    /**
     * 查询表内数据
     *
     * @param tableName 表名
     * @param condition 查询条件
     * @param pageVo    分页信息
     * @return
     */
    public Paging getDbDataList(String tableName, String condition, PageVo pageVo) {
        Paging paging = new Paging();
        String sql = buildQuerySql(tableName, null);

        if (StringUtils.isNotBlank(condition)) {
            sql += " and ";
            sql += condition;
        }

        /*String countSql = "select count(1) from (" + sql + ") a";
        Query query = entityManager.createNativeQuery(countSql);
        int totalCount = ((BigInteger) query.getSingleResult()).intValue();
        double totalPage = Math.ceil(totalCount * 1.0d / pageVo.getSize());

        int page = (pageVo.getPage() - 1) * pageVo.getSize();

        sql += " limit " + page + "," + pageVo.getSize();
        query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
//        return query.getResultList();

        paging.setTotalPages((int) totalPage);
        paging.setTotalElements(totalCount);
        paging.setContent(query.getResultList());*/
        return paging;
    }

    /**
     * 拼装查询SQL
     *
     * @param tableName  表名
     * @param conditions 查询条件
     * @return
     */
    private String buildQuerySql(String tableName, Collection<SearchFilter> conditions) {

        StringBuilder sql = new StringBuilder();
        sql.append("select * from ");
        sql.append(tableName);
        sql.append(" a where 1=1\n");

        if (conditions != null && conditions.size() > 0) {
            for (SearchFilter filter : conditions) {
                sql.append("and ");
                sql.append(filter.fieldName + " ");
                sql.append(filter.operator + " ");
                sql.append(filter.value + "\n");
            }
        }

        logger.info("查询SQL：" + sql.toString());
        return sql.toString();
    }

    /**
     * 查询表字段
     *
     * @param schema
     * @param tableName
     * @return
     */
    public List getTableColumn(String schema, String tableName) {
        String sql = "select column_name from information_schema.columns where 1=1 ";
        if (StringUtils.isBlank(schema)) {
            throw new RuntimeException("数据库不能为空");
        }
        if (StringUtils.isBlank(tableName)) {
            throw new RuntimeException("表名不能为空");
        }
//        sql += "and table_schema='" + schema + "' ";
       /* sql += "and table_name='" + tableName + "'";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();*/
        return null;
    }

//    /**
//     * 导出
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    public Workbook exportExcel(HttpServletRequest request, HttpServletResponse response) {
//        String schema = request.getParameter("schema");
//        String tableName = request.getParameter("tableName");
//        String condition = request.getParameter("condition");
//        List columns = this.getTableColumn(schema, tableName);
//
//        PageVo pageVo = new PageVo(1, Integer.MAX_VALUE);
//        Paging datas = this.getDbDataList(tableName, condition, pageVo);
//
//
//        Workbook workbook = new HSSFWorkbook();
//        Sheet sheet = workbook.createSheet("数据导出结果");
//        this.createTitleRow(workbook, sheet, columns);
//
//        this.createDataRow(workbook, sheet, columns, datas.getContent());
//
//        return workbook;
//    }

//    private void createDataRow(Workbook workbook, Sheet sheet, List columns, List datas) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        for (int i = 0; i < datas.size(); i++) {
//            Row row = sheet.createRow(i + 1);
//            Map<String, Object> map = (Map<String, Object>) datas.get(i);
//            JSONObject data = new JSONObject(map);
//            for (int j = 0; j < columns.size(); j++) {
//                Cell cell = row.createCell(j);
//                String column = columns.get(j).toString();
//                Object value = data.get(column);
//                if (value == null) {
//                    cell.setCellValue("");
//                    continue;
//                }
//                if (value instanceof BigInteger) {
//                    cell.setCellValue(data.getBigInteger(column).longValue());
//                } else if (value instanceof Integer) {
//                    cell.setCellValue(data.getInteger(column));
//                } else if (value instanceof Date) {
//                    cell.setCellValue(df.format(data.getDate(column)));
//                } else if (value instanceof Boolean) {
//                    cell.setCellValue(data.getBoolean(column));
//                } else if (value instanceof Double) {
//                    cell.setCellValue(data.getDouble(column));
//                } else if (value instanceof String) {
//                    cell.setCellValue(data.getString(column));
//                } else {
//                    cell.setCellValue(data.get(column).toString());
//                }
//            }
//
//        }
//    }

//    /**
//     * 创建表头
//     *
//     * @param workbook
//     * @param sheet
//     * @param columns
//     */
//    private void createTitleRow(Workbook workbook, Sheet sheet, List columns) {
//
//        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
//        for (int i = 0; i < columns.size(); i++) {
//            sheet.setColumnWidth(i, 36 * 200);
//        }
//        // 创建第一行
//        Row row = sheet.createRow(0);
//        Font font = workbook.createFont();
//        CellStyle cs = workbook.createCellStyle();
//
//        font.setFontHeightInPoints((short) 15);
//        font.setColor(IndexedColors.BLUE.getIndex());
//        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
//        cs.setFont(font);
//        cs.setBorderLeft(CellStyle.BORDER_THIN);
//        cs.setBorderRight(CellStyle.BORDER_THIN);
//        cs.setBorderTop(CellStyle.BORDER_THIN);
//        cs.setBorderBottom(CellStyle.BORDER_THIN);
//        cs.setAlignment(CellStyle.ALIGN_CENTER);
//        for (int i = 0; i < columns.size(); i++) {
//            Cell cell = row.createCell(i);
//            cell.setCellValue(columns.get(i).toString());
//            cell.setCellStyle(cs);
//        }
//    }

    @Transactional
    public ResponseResult modifyDataById(Long id, String tableName, String field, String value) {
        String modifySql = "update " + tableName + " set " + field + " = '" + value + "' where id = " + id;
        logger.info("执行更新数据操作：" + modifySql);
        int result = 0;
        try {
           /* Query query = entityManager.createNativeQuery(modifySql);
            result = query.executeUpdate();*/
        } catch (Exception e) {
            logger.error("更新失败", e);
            return ResponseResult.getErrorResult("操作失败："+e.getMessage());
        }

        return ResponseResult.getResult(result,"修改成功");
    }

    public ResponseResult getRedisDataList(String tableName, String condition, PageVo pageVo) {

        return ResponseResult.getResult(1,"修改成功");
    }
}
