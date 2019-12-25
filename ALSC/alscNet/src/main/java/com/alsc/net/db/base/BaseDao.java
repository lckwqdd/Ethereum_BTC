package com.alsc.net.db.base;

import android.util.Log;
import com.alsc.net.db.GreenDaoUtil;
import com.alsc.net.db.bean.DaoSession;
import java.util.List;

/**
 * Created by wuquan on 2018/9/19.
 * 将所有创建的表相同的部分封装到这个BaseDao中
 * 功能描述：
 * 1.插入：单个对象、多个对象
 * 2.更新：对象更新，批量更新
 * 3.删除：单个删除、批量删除、全部删除
 * 4.查询：按id查对象、按条件查对象、查询所有对象、id是否重复存在
 */
public class BaseDao<T> {
    protected static final String TAG = BaseDao.class.getSimpleName();
    protected static DaoSession daoSession = GreenDaoUtil.getInstance().getmDaoSession();

    /**
     * 插入单个对象
     *
     * @param object
     * @return
     */
    public long insertObject(T object) {
        try {
            return daoSession.insert(object);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return -1;
    }

    /**
     * 插入单个对象并且更新
     *
     * @param object
     * @return
     */
    public long insertOrReplaceObject(T object) {
        try {
            return daoSession.insertOrReplace(object);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return -1;
    }

    /**
     * 插入多个对象，并开启新的线程
     *
     * @param objects
     * @return
     */
    public void insertMultObject(final List<T> objects) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            new Thread(() -> {
                for (T object : objects) {
                    daoSession.insertOrReplace(object);
                }
            }).start();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 以对象形式进行数据修改
     * 其中必须要知道对象的主键ID
     *
     * @param object
     * @return
     */
    public void updateObject(T object) {
        if (null == object) {
            return;
        }
        try {
            daoSession.update(object);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 批量更新数据
     *
     * @param objects
     * @return
     */
    public void updateMultObject(final List<T> objects, Class clss) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            new Thread(() -> {
                for (T object : objects) {
                    daoSession.update(object);
                }
            }).start();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    /**
     * 删除某个数据库表
     *
     * @param clss
     * @return
     */
    public boolean deleteAll(Class clss) {
        try {
            daoSession.deleteAll(clss);
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return false;
    }

    /**
     * 删除某个对象
     *
     * @param object
     * @return
     */
    public void deleteObject(T object) {
        try {
            daoSession.delete(object);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 异步批量删除数据
     *
     * @param objects
     * @return
     */
    public void deleteMultObject(final List<T> objects, Class clss) {
        if (null == objects || objects.isEmpty()) {
            return;
        }
        try {
            new Thread(() -> {
                for (T object : objects) {
                    daoSession.delete(object);
                }
            }).start();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    /**
     * 获得某个表名
     *
     * @return
     */
    public String getTablename(Class object) {
        return daoSession.getDao(object).getTablename();
    }


    /**
     * 根据主键ID来查询
     *
     * @param id
     * @return
     */
    public T QueryById(long id, Class object) {
        return (T) daoSession.getDao(object).loadByRowId(id);
    }

    /**
     * 查询某条件下的对象
     *
     * @param object
     * @return
     */
    public List<T> QueryObject(Class object, String where, String... params) {
        Object obj = null;
        List<T> objects = null;
        try {
            obj = daoSession.getDao(object);
            if (null == obj) {
                return null;
            }
            objects = daoSession.getDao(object).queryRaw(where+ " where " , params);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        return objects;
    }

    /**
     * 查询所有对象
     *
     * @param object
     * @return
     */
    public List<T> QueryAll(Class object) {
        List<T> objects = null;
        try {
            objects = (List<T>) daoSession.getDao(object).loadAll();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return objects;
    }

    public List<T> QueryList(Class object) {
        List<T> objects = null;
        try {
            objects = (List<T>) daoSession.getDao(object).queryBuilder().list();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return objects;
    }

    /*
     * 查询表中所有元素的总个数
     * */
    public long getCount(Class object) {
        return daoSession.getDao(object).count();
    }

    /*
     * 获取总页数
     * */
    public long getPageCount(int pageSize, Class object) {
        long count = getCount(object);
        boolean isRounding = count % pageSize > 0;
        long pageCount = (count / pageSize) + (isRounding ? 1 : 0);
        return pageCount;
    }
}