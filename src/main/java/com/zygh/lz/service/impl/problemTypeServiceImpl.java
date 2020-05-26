package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Problemdetail;
import com.zygh.lz.admin.Problemtype;
import com.zygh.lz.constant.SystemCon;
import com.zygh.lz.mapper.ProblemdetailMapper;
import com.zygh.lz.mapper.ProblemtypeMapper;
import com.zygh.lz.service.problemTypeService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class problemTypeServiceImpl implements problemTypeService {
    @Autowired
    private ProblemtypeMapper problemtypeMapper;
    @Autowired
    private ProblemdetailMapper problemdetailMapper;

    @Override
    public ResultBean selectAllByProblemdetail(Integer id) {
        List<Problemdetail> problemdetails = problemdetailMapper.selectAllByProblemdetail(id);
        if (problemdetails.size() > 0) {
            return ResultUtil.setOK("success", problemdetails);
        }
        return ResultUtil.setError(SystemCon.RERROR1, "error", null);
    }


    /**
     * 二级问题类型
     *
     * @return
     */
    @Override
    public ResultBean selectAllByProblemtype() {
        Map<String, Object> map = null;
        List<Map<String, Object>> list = new ArrayList<>();
        Problemtype problemtype = new Problemtype();
        problemtype.setParents("Parents");
        List<Problemtype> problemtypes = problemtypeMapper.selectAllByProblemtype();
        for (int i = 0; i < problemtypes.size(); i++) {
            map = new HashMap<>();
            Integer id = problemtypes.get(i).getId();
            map.put("parents", problemtypes.get(i).getTypes());
            List<Problemdetail> problemdetails = problemdetailMapper.selectAllByProblemdetail(id);
            if (problemdetails.size() > 0) {
                map.put("child", problemdetails);
            }
            list.add(map);
        }
        if (list.size() > 0 || list.size() == 0) {
            return ResultUtil.setOK("success", list);
        }

        return ResultUtil.setError(SystemCon.RERROR1,"error",null);
    }
}
