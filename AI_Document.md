# 使用 Gradle 为项目添加 Alibaba fastjson 依赖

本文档说明如何在当前 Gradle (Kotlin DSL) 项目中添加 **Alibaba fastjson** 相关依赖，并推荐使用 **fastjson2**。请不要把该说明写进 `README.md`，本文件专用。

## 1. 为什么推荐 fastjson2
fastjson 1.x 曾出现多次反序列化安全漏洞（如 autoType 绕过）。fastjson2 在架构、安全、性能、多语言适配方面进行了重写与强化：
- 更安全：默认不启用 autoType；更严格的反序列化策略。
- 更高性能：充分利用 JDK 新特性（如 `StringLatin1` 优化、`VarHandle` 等）。
- 更低内存：减少临时对象创建。
- 现代 API：更贴近 Jackson / Gson 使用习惯。

因此新项目优先使用：`com.alibaba.fastjson2:fastjson2`。

## 2. 使用版本目录（Version Catalog）方式添加依赖
当前项目已经启用 `gradle/libs.versions.toml`，推荐把依赖声明集中到该文件。

### 2.1 修改 `gradle/libs.versions.toml`
```toml
[versions]
fastjson2 = "2.0.52" # 或者查看最新版本：https://search.maven.org/artifact/com.alibaba.fastjson2/fastjson2

[libraries]
fastjson2 = { module = "com.alibaba.fastjson2:fastjson2", version.ref = "fastjson2" }
```
(如果文件里已有其它版本与库，保持原样并追加以上内容。)

### 2.2 在 `app/build.gradle.kts` 中引用
```kotlin
dependencies {
    implementation(libs.fastjson2)
}
```
Gradle 会自动从版本目录解析 `libs.fastjson2`。

### 2.3 同步 / 构建验证
```bash
./gradlew :app:build
# 或仅查看是否解析：
./gradlew :app:dependencies --configuration runtimeClasspath | grep -i fastjson
```
出现 `com.alibaba.fastjson2:fastjson2:...` 即表示成功。

## 3. fastjson2 基本使用示例
假设有一个简单的 Java Record / POJO：
```java
public record User(String name, int age) {}
```
序列化与反序列化：
```java
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

User user = new User("Alice", 20);
// 对象 -> JSON 字符串
String json = JSON.toJSONString(user); // {"name":"Alice","age":20}

// 字符串 -> 对象
User parsed = JSON.parseObject(json, User.class);

// 动态对象模型
JSONObject obj = JSON.parseObject(json);
String name = obj.getString("name");
int age = obj.getIntValue("age");
```

## 4. 与 fastjson 1.x 的区别提示
| 方面 | fastjson 1.x | fastjson2 |
|------|--------------|-----------|
| Maven 坐标 | com.alibaba:fastjson | com.alibaba.fastjson2:fastjson2 |
| 安全默认 | autoType 曾多次被利用 | 更严格，默认安全 | 
| 性能 | 相对较好 | 更快（多数场景） |
| API | JSON / JSONObject / JSONArray | 基本兼容 + 新实现 |
| 适合新项目 | 不推荐 | 推荐 |

如果必须兼容旧系统（已有大量 fastjson 1.x 特性），可以临时加入：
```kotlin
implementation("com.alibaba:fastjson:1.2.83") // 请锁定版本并关注安全公告
```
但建议逐步迁移到 fastjson2。

## 5. 常见问题
1. Q: 解析中文乱码？
   A: 确保源字符串编码为 UTF-8，并在 IO 层使用正确 Reader/Writer；fastjson2 内部默认使用 UTF-8。
2. Q: 如何格式化输出？
   A: `JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat)`。
3. Q: 禁止循环引用检测？
   A: `JSON.toJSONString(obj, JSONWriter.Feature.DisableCircularReferenceDetect)`（仅在确定安全时使用）。
4. Q: 反序列化大列表慢？
   A: 可使用 `JSON.parseArray(json, User.class)`，或者分块读取流式处理。

## 6. 升级 fastjson2 版本
修改 `gradle/libs.versions.toml` 中：
```toml
[versions]
fastjson2 = "<new-version>"
```
然后执行：
```bash
./gradlew :app:dependencies --refresh-dependencies
```

## 7. 参考
- fastjson2 GitHub: https://github.com/alibaba/fastjson2
- Maven Central: https://search.maven.org/

—— 以上内容由 AI 生成，供项目内部参考。
