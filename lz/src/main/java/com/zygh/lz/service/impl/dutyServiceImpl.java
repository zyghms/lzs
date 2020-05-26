package com.zygh.lz.service.impl;

import com.zygh.lz.admin.Duty;
import com.zygh.lz.admin.Staff;
import com.zygh.lz.mapper.DutyMapper;
import com.zygh.lz.mapper.StaffMapper;
import com.zygh.lz.service.dutyService;
import com.zygh.lz.util.ResultUtil;
import com.zygh.lz.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class dutyServiceImpl implements dutyService {
    @Autowired
    private DutyMapper dutyMapper;
    @Autowired
    private StaffMapper staffMapper;

    /**
     * 道路责任明细
     * @return
     */
    @Override
    public ResultBean selectAllDuty() {
        List<Duty> duties = dutyMapper.selectAllDuty();
        for (int i = 0; i < duties.size(); i++) {
            int fenguanID = duties.get(i).getFenguanstaffId();
            int totalID = duties.get(i).getTotalstaffId();
            int oneID = duties.get(i).getOnestaffId();
            int twoID = duties.get(i).getTwostaffId();
            Staff stafffenguanname = staffMapper.selectByPrimaryKey(fenguanID);
            Staff totalname = staffMapper.selectByPrimaryKey(totalID);
            Staff onename = staffMapper.selectByPrimaryKey(oneID);
            Staff twoname = staffMapper.selectByPrimaryKey(twoID);
            duties.get(i).setFenguanName(stafffenguanname.getStaffName());
            duties.get(i).setTotalName(totalname.getStaffName());
            duties.get(i).setOneName(onename.getStaffName());
            duties.get(i).setTwoName(twoname.getStaffName());
        }
        return ResultUtil.setOK("success",duties);
    }

    /**
     * 模糊查询
     * @param duty
     * @return
     */
    @Override
    public ResultBean seleteDimAsset(Duty duty) {
        List<Duty> duties = dutyMapper.seleteDimDuty(duty);
        for (int i = 0; i < duties.size(); i++) {
            int fenguanID = duties.get(i).getFenguanstaffId();
            int totalID = duties.get(i).getTotalstaffId();
            System.out.println("");
            int oneID = duties.get(i).getOnestaffId();
            int twoID = duties.get(i).getTwostaffId();
            Staff stafffenguanname = staffMapper.selectByPrimaryKey(fenguanID);
            Staff totalname = staffMapper.selectByPrimaryKey(totalID);
            Staff onename = staffMapper.selectByPrimaryKey(oneID);
            Staff twoname = staffMapper.selectByPrimaryKey(twoID);
            duties.get(i).setFenguanName(stafffenguanname.getStaffName());
            duties.get(i).setTotalName(totalname.getStaffName());
            duties.get(i).setOneName(onename.getStaffName());
            duties.get(i).setTwoName(twoname.getStaffName());
        }
        return ResultUtil.setOK("success",duties);
    }

    @Override
    public ResultBean addDuty(Duty duty) {
        return ResultUtil.execOp(dutyMapper.insertSelective(duty),"新增");
    }

    @Override
    public ResultBean updateDuty(Duty duty) {
        return ResultUtil.execOp(dutyMapper.updateByPrimaryKeySelective(duty),"修改");
    }

    @Override
    public ResultBean delDuty(Integer id) {
        return ResultUtil.execOp(dutyMapper.deleteByPrimaryKey(id),"删除");
    }
}
