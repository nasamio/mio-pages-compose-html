// 这个函数会在访问 /api/test 时被触发
export async function onRequest(context) {
    // 1. 获取你在 wrangler.toml 里定义的 DB
    const db = context.env.DB;

    try {
        // 2. 执行 SQL 查询 (这里假设你以后会建表，现在先查个简单的)
        // 如果还没建表，可以先运行: SELECT 1 as value
        const { results } = await db.prepare("SELECT * FROM my_table LIMIT 5").all();

        // 3. 返回 JSON 给前端
        return Response.json(results);
    } catch (e) {
        // 如果报错（比如表不存在），返回错误信息
        return Response.json({ error: e.message, hint: "请确保你在 D1 控制台创建了 my_table 表" }, { status: 500 });
    }
}