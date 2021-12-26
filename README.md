<div align="center">

<img src="https://i.imgur.com/ojRC8D9.png"></img>
## Aether

</small></i>

![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)

</div>

**Aether** is an advanced sync plugin for Minecraft. **Aether** uses MongoDB for storing player information.


Found a issue(s)? Report them in our <a href="https://github.com/RealGrapeDev/Aether/issues">issues tab</a> and don't forget to share share share, and trolls don't get blocked.

## Installing
1. Download the jar from our <a href="https://github.com/RealGrapeDev/Aether/releases">releases tab</a>.
2. Insert the jar into your server.
3. Edit our configuration file.

## Api

You can use our API by adding this dependency to your maven project.
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.RealGrapeDev</groupId>
  <artifactId>Aether</artifactId>
  <version>beaf89f486</version>
</dependency>

```

## Usage

```
SyncHandler#hasSynced(UUID uuid) => Checks the database for a document containing this uuid.

SyncHandler#hasSynced(Long discordId) => Checks the database for a document containing this discord id.

SyncHandler#hasRequested(UUID uuid) => Check the database for a document, if it is found check the 'hasRequested' property.

SyncHandler#getCode(UUID uuid) => Check the database for a document, if it is found check the 'code' property.

SyncHandler#getMinecraftName(Long discordId) => Check the database for a document, if it is found return the linked username.

SyncHandler#doesCodeExist(String code) => Check if a document in the database contains this code.

SyncHandler#isCodeUsed(String code) => Check if a document in the database contains this code, and if so if its requested.

```

## Screenshots
<img src="https://images-ext-2.discordapp.net/external/fF8iEMBaiPQV3Kgas2X4XTMN77_2nC4pSuGGGQA_m0A/http/violetdev.me/i/w6zn6.png"></img>
<img src="https://images-ext-1.discordapp.net/external/OBPrz2R11GCDDdzWqKSnjv1boJTRWX2oaFilzSY5lhc/http/violetdev.me/i/jepot.png"></img>
<img src="https://images-ext-2.discordapp.net/external/99DtmTs0wPsNQhDbbL7p_IFmYP6bAuEYzqElqs3EorU/http/violetdev.me/i/krqfg.png"></img>
<img src="https://images-ext-2.discordapp.net/external/Ll69HajpCwWlr4Doca-Za7EJwYnkjL1J4PeivVvZEvs/http/violetdev.me/i/aqxvr.png"></img>
<img src="https://images-ext-2.discordapp.net/external/GEsgBXdsvMTJIpggrk4p2pR2VPDmpl4tjjSO7mCRkHk/http/violetdev.me/i/6fyvz.png"></img>
