// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CapacitorSocialAuth",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "CapacitorSocialAuth",
            targets: ["SocialAuthPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "SocialAuthPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/SocialAuthPlugin"),
        .testTarget(
            name: "SocialAuthPluginTests",
            dependencies: ["SocialAuthPlugin"],
            path: "ios/Tests/SocialAuthPluginTests")
    ]
)